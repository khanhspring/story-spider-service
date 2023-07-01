package com.webspider.infrastructure.converter;

import com.webspider.application.model.response.StoryResponse;
import com.webspider.infrastructure.model.entity.JpaStory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StoryResponseConverter implements Converter<JpaStory, StoryResponse> {

    private final AuthorResponseConverter authorResponseConverter;
    private final GenreResponseConverter genreResponseConverter;

    @Nullable
    @Override
    public StoryResponse convert(@Nullable JpaStory source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return StoryResponse.builder()
                .title(source.getTitle())
                .slug(source.getSlug())
                .summary(source.getSummary())
                .author(authorResponseConverter.convert(source.getAuthor()))
                .genres(genreResponseConverter.convert(source.getGenres()))
                .build();
    }
}
