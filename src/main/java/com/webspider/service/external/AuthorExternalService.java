package com.webspider.service.external;

import com.webspider.application.model.request.AuthorSearchRequest;
import com.webspider.application.model.response.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorExternalService {
    Page<AuthorResponse> search(AuthorSearchRequest request, Pageable pageable);
    AuthorResponse findByCode(String code);
}
