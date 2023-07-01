package com.webspider.service.job.executor;

import com.webspider.infrastructure.model.entity.crawl.JpaCrawlChaptersJob;

public interface CrawlChaptersJobExecutor {

    void execute(JpaCrawlChaptersJob job);
}
