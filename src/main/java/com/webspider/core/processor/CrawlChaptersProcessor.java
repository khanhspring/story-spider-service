package com.webspider.core.processor;

import com.webspider.core.model.ChapterResult;
import com.webspider.core.model.CrawlChaptersOption;
import com.webspider.infrastructure.model.enumeration.SourceCode;

import java.util.function.Function;

public interface CrawlChaptersProcessor {

    void process(CrawlChaptersOption option, Function<ChapterResult, Boolean> doNext);
    boolean isSupported(SourceCode sourceCode);
}
