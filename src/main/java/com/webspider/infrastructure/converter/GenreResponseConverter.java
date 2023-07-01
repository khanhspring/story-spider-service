package com.webspider.infrastructure.converter;

import com.webspider.application.model.response.GenreResponse;
import com.webspider.infrastructure.model.entity.JpaGenre;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class GenreResponseConverter implements Converter<JpaGenre, GenreResponse> {

    @Nullable
    @Override
    public GenreResponse convert(@Nullable JpaGenre source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return GenreResponse.builder()
                .code(source.getCode())
                .title(source.getTitle())
                .build();
    }

    @Nullable
    public List<GenreResponse> convert(@Nullable Collection<JpaGenre> sources) {
        return sources.stream()
                .map(this::convert)
                .toList();
    }
}
