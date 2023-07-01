package com.webspider.service.external.impl;

import com.webspider.application.model.response.ChapterResponse;
import com.webspider.application.model.response.ChapterSimpleResponse;
import com.webspider.infrastructure.converter.ChapterResponseConverter;
import com.webspider.infrastructure.converter.ChapterSimpleResponseConverter;
import com.webspider.infrastructure.exception.ResourceNotFoundException;
import com.webspider.infrastructure.repository.JpaChapterRepository;
import com.webspider.service.external.ChapterExternalService;
import com.webspider.service.storyview.StoryViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChapterExternalServiceImpl implements ChapterExternalService {

    private final JpaChapterRepository jpaChapterRepository;
    private final ChapterResponseConverter chapterResponseConverter;
    private final StoryViewService storyViewService;
    private final ChapterSimpleResponseConverter chapterSimpleResponseConverter;

    @Override
    @Transactional(readOnly = true)
    public Page<ChapterSimpleResponse> findByStory(String storySlug, Pageable pageable) {
        return jpaChapterRepository.findAllByStorySlug(storySlug, pageable)
                .map(chapterSimpleResponseConverter::convert);
    }

    @Override
    @Transactional(readOnly = true)
    public ChapterResponse findByStorySlugAndIndex(String storySlug, int index) {
        var chapter = jpaChapterRepository.findByStorySlugAndIndex(storySlug, index)
                .orElseThrow(ResourceNotFoundException::new);
        storyViewService.increaseView(chapter.getStory().getId());
        return chapterResponseConverter.convert(chapter);
    }
}
