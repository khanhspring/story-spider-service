package com.webspider.core.jsoup.action;

import com.webspider.core.ElementExtractor;
import com.webspider.core.action.CrawlerAction;
import com.webspider.core.jsoup.JsoupElementLocatorSelector;
import com.webspider.core.jsoup.JsoupExtractor;
import com.webspider.core.selenium.SeleniumElementLocatorConverter;
import com.webspider.infrastructure.exception.ApplicationException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsoupExtractDataAction implements CrawlerAction<JsoupActionContext, ElementExtractor, List<Map<String, String>>> {

    private final JsoupElementLocatorSelector jsoupElementLocatorSelector = new JsoupElementLocatorSelector();
    private final JsoupExtractor jsoupExtractor = new JsoupExtractor();

    @Override
    public List<Map<String, String>> execute(JsoupActionContext context, ElementExtractor elementExtractor) {
        var document = context.getDocument();

        if (Objects.nonNull(elementExtractor.getElementLocator())) {
            var element = jsoupElementLocatorSelector.select(document, elementExtractor.getElementLocator())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ApplicationException("Element " + elementExtractor.getElementLocator().toString() + " not found"));

            return jsoupExtractor.extract(element, elementExtractor.getExtractors());
        }

        var elements = jsoupElementLocatorSelector.select(document, elementExtractor.getElementsLocator());
        return jsoupExtractor.extract(elements, elementExtractor.getExtractors());
    }
}
