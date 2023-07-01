package com.webspider.service.job.executor.impl;

import com.webspider.core.model.StoryInfoResult;
import com.webspider.infrastructure.exception.PoolEmptyException;
import com.webspider.infrastructure.model.entity.crawl.JpaCrawlStoryJob;
import com.webspider.infrastructure.model.enumeration.StoryStatus;
import com.webspider.infrastructure.repository.JpaStoryRepository;
import com.webspider.infrastructure.util.FileUtils;
import com.webspider.infrastructure.util.ImageUtils;
import com.webspider.service.author.AuthorService;
import com.webspider.service.crawlstoryjob.CrawlStoryJobService;
import com.webspider.service.job.executor.CrawlStoryJobExecutor;
import com.webspider.service.genre.GenreService;
import com.webspider.service.processor.CrawlStoryInfoProcessorFactory;
import com.webspider.service.storage.StorageService;
import com.webspider.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoryJobExecutorImpl implements CrawlStoryJobExecutor {

    private final CrawlStoryInfoProcessorFactory processorFactory;
    private final JpaStoryRepository jpaStoryRepository;
    private final CrawlStoryJobService crawlStoryJobService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final TagService tagService;
    private final StorageService storageService;

    @Async
    public void execute(JpaCrawlStoryJob job) {
        try {
            log.info("Start crawling story job id [{}] for story [{}]", job.getId(), job.getStory().getSlug());
            crawlStoryJobService.start(job.getId());
            var url = job.getStory().getExternalUrl();
            log.info("Start crawling story [{}]", url);

            processorFactory.get(job.getStory().getSource().getCode())
                    .process(url, (r) -> doOnNext(job, r));

            crawlStoryJobService.complete(job.getId());
            log.info("Completed crawling story job id [{}] for story [{}]", job.getId(), job.getStory().getSlug());
        } catch (PoolEmptyException e) {
            log.warn("{}. Stopped!", e.getMessage());
            crawlStoryJobService.ready(job.getId());
        } catch (Exception e) {
            log.warn("Cannot start crawling stories job id [{}] for story [{}]", job.getId(), job.getStory().getSlug(), e);
            crawlStoryJobService.onFailed(job.getId(), e.getMessage());
        }
    }

    private void doOnNext(JpaCrawlStoryJob job, StoryInfoResult result) {
        var story = jpaStoryRepository.findById(job.getStory().getId())
                .orElseThrow();
        var author = authorService.findByNameOrElseCreate(result.getAuthorName());
        story.setAuthor(author);
        story.setCompleted(result.isCompleted());
        story.setSummary(result.getSummary());
        story.setTitle(result.getTitle());

        if (Objects.nonNull(result.getRating())
                && Objects.nonNull(result.getTotalRating())) {
            story.setRating(result.getRating());
            story.setTotalRating(result.getTotalRating());
        }

        var thumbnailImg = downloadThumbnailAndUpload(result.getThumbnailUrl(), story.getSlug());
        story.setThumbnailUrl(thumbnailImg);

        if (!ObjectUtils.isEmpty(result.getGenres())) {
            var genres = result.getGenres().stream()
                    .map(g -> genreService.findByTitleOrElseCreate(g.getName()))
                    .collect(Collectors.toSet());
            story.setGenres(genres);
        }

        if (!ObjectUtils.isEmpty(result.getTags())) {
            var tags = result.getTags().stream()
                    .map(t -> tagService.findByTitleOrElseCreate(t.getName()))
                    .collect(Collectors.toSet());
            story.setTags(tags);
        }

        story.setStatus(StoryStatus.Active);

        jpaStoryRepository.save(story);
    }

    private String downloadThumbnailAndUpload(String thumbnailUrl, String storySlug) {
        var imageContent = ImageUtils.downloadImageFromUrl(thumbnailUrl);
        var extension = FileUtils.getFileExtension(thumbnailUrl);
        var newFileName = storySlug + "." + extension;
        storageService.uploadStoryThumbnail(imageContent, newFileName);
        return newFileName;
    }
}
