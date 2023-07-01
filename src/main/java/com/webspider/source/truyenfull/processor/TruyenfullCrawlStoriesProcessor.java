package com.webspider.source.truyenfull.processor;

import com.webspider.core.jsoup.action.JsoupActionContext;
import com.webspider.core.model.PageResult;
import com.webspider.core.model.StoriesResult;
import com.webspider.core.processor.CrawlStoriesProcessor;
import com.webspider.infrastructure.exception.ApplicationException;
import com.webspider.infrastructure.model.enumeration.SourceCode;
import com.webspider.infrastructure.pool.executorpool.StoryListingJsoupExecutorPool;
import com.webspider.infrastructure.util.DelayUtils;
import com.webspider.infrastructure.converter.StoriesResultConverter;
import com.webspider.source.truyenfull.processor.helper.TruyenfullCrawlStoryProcessor;
import com.webspider.source.truyenfull.util.TruyenfullUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class TruyenfullCrawlStoriesProcessor implements CrawlStoriesProcessor {

    private final StoryListingJsoupExecutorPool executorPool;
    private final StoriesResultConverter storiesResultConverter;
    private final TruyenfullCrawlStoryProcessor truyenfullCrawlStoryProcessor;
    private static final Duration DELAY_BETWEEN_PAGES = Duration.ofSeconds(1);

    public void process(String baseUrl, int fromPageNumber, Function<StoriesResult, Boolean> doNext) {
        var future = executorPool.execute(() -> {
            var context = JsoupActionContext.init();
            var pageNumber = fromPageNumber;
            var hasNext = false;
            do {
                String pageNumberPath = "";
                if (pageNumber > 1) {
                    pageNumberPath = "trang-" + pageNumber + "/";
                }
                var url = baseUrl.replace("{pageNumber}", pageNumberPath);
                log.info("[Truyenfull] Crawling stories url [{}]", url);
                var pageResult = truyenfullCrawlStoryProcessor.process(context, url);
                var canNext = doNext(doNext, pageResult, pageNumber);
                hasNext = pageResult.hasNext() && canNext;
                pageNumber++;

                DelayUtils.delay(DELAY_BETWEEN_PAGES);
            } while (hasNext);
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        } catch (ExecutionException e) {
            throw new ApplicationException(e.getCause());
        }
    }

    private boolean doNext(Function<StoriesResult, Boolean> doNext, PageResult<List<Map<String, String>>> pageResult, int pageNumber) {
        if (Objects.isNull(doNext)) {
            return true;
        }
        var storiesResult = storiesResultConverter.convert(pageResult, pageNumber, TruyenfullUtils::extractStorySlug);
        return doNext.apply(storiesResult);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Truyenfull;
    }
}
