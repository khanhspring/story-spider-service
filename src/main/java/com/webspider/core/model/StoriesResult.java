package com.webspider.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoriesResult {
    private int pageNumber;
    private List<StoryResult> stories;
}
