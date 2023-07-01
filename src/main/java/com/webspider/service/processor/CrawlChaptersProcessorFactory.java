package com.webspider.service.processor;

import com.webspider.core.processor.CrawlChaptersProcessor;
import com.webspider.infrastructure.model.enumeration.SourceCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlChaptersProcessorFactory {

    private final List<CrawlChaptersProcessor> processors;

    public CrawlChaptersProcessor get(SourceCode sourceCode) {
        for (var p : processors) {
            if (p.isSupported(sourceCode)) {
                return p;
            }
        }
        throw new UnsupportedOperationException("Source code is not supported");
    }
}
