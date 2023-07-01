package com.webspider.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResponse {
    private int index;
    private String title;
    private String content;
    private StorySimpleResponse story;
}
