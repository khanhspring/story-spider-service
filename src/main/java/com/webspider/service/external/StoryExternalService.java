package com.webspider.service.external;

import com.webspider.application.model.request.StorySearchRequest;
import com.webspider.application.model.response.StoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoryExternalService {
    Page<StoryResponse> search(StorySearchRequest request, Pageable pageable);
    Page<StoryResponse> findByGenre(String genreCode, Pageable pageable);
    Page<StoryResponse> findByAuthor(String authorCode, Pageable pageable);
    StoryResponse findBySlug(String slug);
}
