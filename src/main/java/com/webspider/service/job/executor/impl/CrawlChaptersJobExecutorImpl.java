package com.webspider.service.job.executor.impl;

import com.webspider.core.model.ChapterResult;
import com.webspider.core.model.CrawlChaptersOption;
import com.webspider.infrastructure.exception.PoolEmptyException;
import com.webspider.infrastructure.model.entity.JpaChapter;
import com.webspider.infrastructure.model.entity.JpaStory;
import com.webspider.infrastructure.model.entity.crawl.JpaCrawlChaptersJob;
import com.webspider.infrastructure.repository.JpaChapterRepository;
import com.webspider.service.crawlchaptersjob.CrawlChaptersJobService;
import com.webspider.service.job.executor.CrawlChaptersJobExecutor;
import com.webspider.service.processor.CrawlChaptersProcessorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlChaptersJobExecutorImpl implements CrawlChaptersJobExecutor {

    private final CrawlChaptersProcessorFactory processorFactory;
    private final CrawlChaptersJobService crawlChaptersJobService;
    private final JpaChapterRepository jpaChapterRepository;

    @Async
    public void execute(JpaCrawlChaptersJob job) {
        try {
            log.info("Start crawling chapters job id [{}] for chapters [{}]", job.getId(), job.getStory().getSlug());
            crawlChaptersJobService.start(job.getId());
            log.info("Start crawling chapters for story [{}]", job.getStory().getSlug());

            int startIndex = job.getStartIndex();
            if (Objects.nonNull(job.getCurrentIndex()) && job.getCurrentIndex() > 0) {
                startIndex = job.getCurrentIndex();
            }
            var chapterNumber = new AtomicInteger(startIndex);

            var option = CrawlChaptersOption.builder()
                    .storyUrl(job.getStory().getExternalUrl())
                    .startUrl(job.getStartUrl())
                    .startIndex(startIndex)
                    .build();
            processorFactory.get(job.getStory().getSource().getCode())
                    .process(option, (r) -> doOnNext(job, r, chapterNumber));

            crawlChaptersJobService.complete(job.getId());
            log.info("Completed crawling chapters job id [{}] for chapters [{}]", job.getId(), job.getStory().getSlug());
        } catch (PoolEmptyException e) {
            log.warn("{}. Stopped!", e.getMessage());
            crawlChaptersJobService.ready(job.getId());
        } catch (Exception e) {
            log.warn("Cannot start crawling stories job id [{}] for chapters [{}]", job.getId(), job.getStory().getSlug(), e);
            crawlChaptersJobService.onFailed(job.getId(), e.getMessage());
        }
    }

    private boolean doOnNext(JpaCrawlChaptersJob job, ChapterResult result, AtomicInteger chapterNumber) {
        crawlChaptersJobService.updateCurrentChapter(job.getId(), result.getChapterNumber(), result.getUrl());
        chapterNumber.set(result.getChapterNumber());

        if (ObjectUtils.isEmpty(result.getTitle())) {
            // do nothing
            return true;
        }

        var chapterOpt = jpaChapterRepository.findByStoryIdAndIndex(job.getStory().getId(), result.getChapterNumber());
        if (chapterOpt.isEmpty()) {
            addChapter(result, job.getStory());
            return true;
        }
        updateChapter(result, chapterOpt.get());
        return true;
    }

    public void addChapter(ChapterResult result, JpaStory story) {
        var chapter = JpaChapter.builder()
                .story(story)
                .index(result.getChapterNumber())
                .title(result.getTitle())
                .content(result.getContent())
                .externalUrl(result.getUrl())
                .build();
        jpaChapterRepository.save(chapter);
    }

    public void updateChapter(ChapterResult result, JpaChapter chapter) {
        chapter.setTitle(result.getTitle());
        chapter.setContent(result.getContent());
        jpaChapterRepository.save(chapter);
    }
}
