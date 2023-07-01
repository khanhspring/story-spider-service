package com.webspider.core.jsoup.action;

import com.webspider.core.action.CrawlerAction;
import com.webspider.infrastructure.exception.PageHasBeenRedirectedException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class JsoupAccessPageAction implements CrawlerAction<JsoupActionContext, String, Void>  {

    @Override
    public Void execute(JsoupActionContext context, String url) {
        try {
            var document = Jsoup.connect(url)
                    .followRedirects(context.isFollowRedirects())
                    .get();
            if (document.connection().response().statusCode() == 301) {
                throw new PageHasBeenRedirectedException();
            }
            context.setDocument(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
