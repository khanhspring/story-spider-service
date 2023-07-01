package com.webspider.core.processor;

import com.webspider.core.model.StoriesResult;
import com.webspider.infrastructure.model.enumeration.SourceCode;

import java.util.function.Function;

public interface CrawlStoriesProcessor {

    void process(String baseUrl, int fromPageNumber, Function<StoriesResult, Boolean> doNext);
    boolean isSupported(SourceCode sourceCode);
}
