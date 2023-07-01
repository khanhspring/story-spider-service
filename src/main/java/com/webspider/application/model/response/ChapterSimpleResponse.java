package com.webspider.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterSimpleResponse {
    private int index;
    private String title;
}
