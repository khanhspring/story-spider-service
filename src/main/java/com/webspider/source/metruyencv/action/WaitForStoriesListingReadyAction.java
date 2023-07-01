package com.webspider.source.metruyencv.action;

import com.webspider.core.action.ActionInput;
import com.webspider.core.action.CrawlerAction;
import com.webspider.core.selenium.action.SeleniumActionContext;
import com.webspider.core.selenium.util.WaitUtils;
import org.openqa.selenium.By;

public class WaitForStoriesListingReadyAction implements CrawlerAction<SeleniumActionContext, ActionInput, Void> {

    public Void execute(SeleniumActionContext context, ActionInput input) {
        var webDriver = context.getWebDriver();
        WaitUtils.forVisibility(webDriver, By.xpath("//*[@id=\"bookGrid\"]//div[contains(@class, \"media-body\")]//a"));
        return null;
    }
}
