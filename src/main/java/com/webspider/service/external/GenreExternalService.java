package com.webspider.service.external;

import com.webspider.application.model.request.GenreSearchRequest;
import com.webspider.application.model.response.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreExternalService {
    Page<GenreResponse> search(GenreSearchRequest request, Pageable pageable);
    GenreResponse findByCode(String code);
}
