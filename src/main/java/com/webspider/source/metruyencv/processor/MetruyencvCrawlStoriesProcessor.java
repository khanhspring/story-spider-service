package com.webspider.source.metruyencv.processor;

import com.webspider.core.model.PageResult;
import com.webspider.core.processor.CrawlStoriesProcessor;
import com.webspider.core.selenium.action.SeleniumActionContext;
import com.webspider.infrastructure.exception.WebDriverPoolEmptyException;
import com.webspider.core.model.StoriesResult;
import com.webspider.infrastructure.model.enumeration.SourceCode;
import com.webspider.infrastructure.pool.webdriverpool.StoryListingWebDriverPool;
import com.webspider.infrastructure.converter.StoriesResultConverter;
import com.webspider.source.metruyencv.processor.helper.MetruyencvCrawlStoryProcessor;
import com.webspider.source.metruyencv.util.MetruyencvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetruyencvCrawlStoriesProcessor implements CrawlStoriesProcessor {

    private final StoryListingWebDriverPool webDriverPool;
    private final StoriesResultConverter storiesResultConverter;
    private final MetruyencvCrawlStoryProcessor metruyencvCrawlStoryProcessor;

    public void process(String baseUrl, int fromPageNumber, Function<StoriesResult, Boolean> doNext) {
        var webDriver = webDriverPool.get().orElseThrow(WebDriverPoolEmptyException::new);
        try {
            var context = SeleniumActionContext.init(webDriver);
            var pageNumber = fromPageNumber;
            var hasNext = false;
            do {
                var url = baseUrl.replace("{pageNumber}", pageNumber + "");
                log.info("[Metruyencv] Crawling stories url [{}]", url);

                var pageResult = metruyencvCrawlStoryProcessor.process(context, url);

                var canNext = doNext(doNext, pageResult, pageNumber);

                hasNext = pageResult.hasNext() && canNext;
                pageNumber++;
            } while (hasNext);
        } finally {
            webDriverPool.release(webDriver);
        }
    }

    private boolean doNext(Function<StoriesResult, Boolean> doNext, PageResult<List<Map<String, String>>> pageResult, int pageNumber) {
        if (Objects.isNull(doNext)) {
            return true;
        }
        var storiesResult = storiesResultConverter.convert(pageResult, pageNumber, MetruyencvUtils::extractStorySlug);
        return doNext.apply(storiesResult);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Metruyencv;
    }
}
