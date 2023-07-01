package com.webspider.service.external.impl;

import com.webspider.application.model.request.AuthorSearchRequest;
import com.webspider.application.model.response.AuthorResponse;
import com.webspider.infrastructure.converter.AuthorResponseConverter;
import com.webspider.infrastructure.exception.ResourceNotFoundException;
import com.webspider.infrastructure.repository.JpaAuthorRepository;
import com.webspider.service.external.AuthorExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorExternalServiceImpl implements AuthorExternalService {

    private final JpaAuthorRepository jpaAuthorRepository;
    private final AuthorResponseConverter authorResponseConverter;

    @Override
    public Page<AuthorResponse> search(AuthorSearchRequest request, Pageable pageable) {
        return jpaAuthorRepository.search(request, pageable)
                .map(authorResponseConverter::convert);
    }

    @Override
    public AuthorResponse findByCode(String code) {
        return jpaAuthorRepository.findByCode(code)
                .map(authorResponseConverter::convert)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
