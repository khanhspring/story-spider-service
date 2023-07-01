package com.webspider.service.crawlstoryjob;

public interface CrawlStoryJobService {
    void start(Long id);
    void complete(Long id);
    void ready(Long id);
    void onFailed(Long id, String errorMessage);
}
