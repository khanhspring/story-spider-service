package com.webspider.service.external.impl;

import com.webspider.application.model.request.GenreSearchRequest;
import com.webspider.application.model.response.GenreResponse;
import com.webspider.infrastructure.converter.GenreResponseConverter;
import com.webspider.infrastructure.exception.ResourceNotFoundException;
import com.webspider.infrastructure.repository.JpaGenreRepository;
import com.webspider.service.external.GenreExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreExternalServiceImpl implements GenreExternalService {

    private final JpaGenreRepository jpaGenreRepository;
    private final GenreResponseConverter genreResponseConverter;

    @Override
    public Page<GenreResponse> search(GenreSearchRequest request, Pageable pageable) {
        return jpaGenreRepository.search(request, pageable)
                .map(genreResponseConverter::convert);
    }

    @Override
    public GenreResponse findByCode(String code) {
        return jpaGenreRepository.findByCode(code)
                .map(genreResponseConverter::convert)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
