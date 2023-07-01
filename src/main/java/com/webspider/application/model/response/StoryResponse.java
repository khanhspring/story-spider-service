package com.webspider.application.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponse {
    private String slug;
    private String title;
    private String summary;
    private String thumbnailUrl;
    private boolean completed;

    private AuthorResponse author;

    private List<GenreResponse> genres;
}
