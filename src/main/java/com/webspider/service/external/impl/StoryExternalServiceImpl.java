package com.webspider.service.external.impl;

import com.webspider.application.model.request.StorySearchRequest;
import com.webspider.application.model.response.StoryResponse;
import com.webspider.infrastructure.converter.StoryResponseConverter;
import com.webspider.infrastructure.exception.ResourceNotFoundException;
import com.webspider.infrastructure.model.enumeration.StoryStatus;
import com.webspider.infrastructure.repository.JpaStoryRepository;
import com.webspider.service.external.StoryExternalService;
import com.webspider.service.storyview.StoryViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StoryExternalServiceImpl implements StoryExternalService {

    private final JpaStoryRepository jpaStoryRepository;
    private final StoryViewService storyViewService;
    private final StoryResponseConverter storyResponseConverter;

    @Override
    public Page<StoryResponse> search(StorySearchRequest request, Pageable pageable) {
        return jpaStoryRepository.search(request, pageable)
                .map(storyResponseConverter::convert);
    }

    @Override
    public Page<StoryResponse> findByGenre(String genreCode, Pageable pageable) {
        return jpaStoryRepository.findAllByGenresCode(genreCode, pageable)
                .map(storyResponseConverter::convert);
    }

    @Override
    public Page<StoryResponse> findByAuthor(String authorCode, Pageable pageable) {
        return jpaStoryRepository.findAllByAuthorCode(authorCode, pageable)
                .map(storyResponseConverter::convert);
    }

    @Override
    @Transactional(readOnly = true)
    public StoryResponse findBySlug(String slug) {
        var story = jpaStoryRepository.findBySlugAndStatus(slug, StoryStatus.Active)
                .orElseThrow(ResourceNotFoundException::new);
        storyViewService.increaseView(story.getId());
        return storyResponseConverter.convert(story);
    }
}
