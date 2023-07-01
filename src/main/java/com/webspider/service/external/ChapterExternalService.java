package com.webspider.service.external;

import com.webspider.application.model.response.ChapterResponse;
import com.webspider.application.model.response.ChapterSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChapterExternalService {
    Page<ChapterSimpleResponse> findByStory(String storySlug, Pageable pageable);
    ChapterResponse findByStorySlugAndIndex(String storySlug, int index);
}
