package com.webspider.service.job.executor;

import com.webspider.infrastructure.model.entity.crawl.JpaCrawlStoryJob;

public interface CrawlStoryJobExecutor {

    void execute(JpaCrawlStoryJob job);
}
