package com.webspider.infrastructure.converter;

import com.webspider.application.model.response.AuthorResponse;
import com.webspider.infrastructure.model.entity.JpaAuthor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorResponseConverter implements Converter<JpaAuthor, AuthorResponse> {

    @Nullable
    @Override
    public AuthorResponse convert(@Nullable JpaAuthor source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return AuthorResponse.builder()
                .code(source.getCode())
                .fullName(source.getFullName())
                .build();
    }
}
