package com.webspider.source.truyenfull.processor.helper;

import com.webspider.core.ElementExtractor;
import com.webspider.core.jsoup.action.JsoupActionContext;
import com.webspider.core.jsoup.action.JsoupActionExecutor;
import com.webspider.core.selector.ElementLocator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TruyenfullCrawlFirstChapterUrlProcessor {

    public String process(String storyUrl) {
            var context = JsoupActionContext.init();
            var executor = new JsoupActionExecutor(context);
            executor.access(storyUrl);

            var firstChapterExtractor = ElementExtractor
                    .element(ElementLocator.cssSelector("#list-chapter > .row > div:nth-child(1) > ul > li:nth-child(1) a"))
                    .attr("chapter_url", "href");
            var firstChapter = executor.extractSingleData(firstChapterExtractor);

            return firstChapter.get("chapter_url");
    }

}
