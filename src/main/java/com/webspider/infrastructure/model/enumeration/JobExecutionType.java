package com.webspider.infrastructure.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobExecutionType {
    CrawlStories,
    CrawlStory,
    CrawlChapters
}
