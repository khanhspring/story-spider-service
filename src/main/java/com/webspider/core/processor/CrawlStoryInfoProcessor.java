package com.webspider.core.processor;

import com.webspider.core.model.StoryInfoResult;
import com.webspider.infrastructure.model.enumeration.SourceCode;

import java.util.function.Consumer;

public interface CrawlStoryInfoProcessor {

    void process(String url, Consumer<StoryInfoResult> callback);
    boolean isSupported(SourceCode sourceCode);
}
