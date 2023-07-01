package com.webspider.service.job.executor;

import com.webspider.infrastructure.model.entity.crawl.JpaCrawlStoriesJob;

public interface CrawlStoriesJobExecutor {

    void execute(JpaCrawlStoriesJob job);
}
