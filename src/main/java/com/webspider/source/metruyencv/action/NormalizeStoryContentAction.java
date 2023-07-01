package com.webspider.source.metruyencv.action;

import com.webspider.core.action.ActionInput;
import com.webspider.core.action.CrawlerAction;
import com.webspider.core.selenium.action.SeleniumActionContext;
import com.webspider.core.selenium.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NormalizeStoryContentAction implements CrawlerAction<SeleniumActionContext, ActionInput, Void> {

    public Void execute(SeleniumActionContext context, ActionInput input) {
        var script = """
                CanvasRenderingContext2D.prototype.__oldFillText = CanvasRenderingContext2D.prototype.fillText;
                function createP(order, text) {
                    const p = document.createElement('p');
                    if (text && text.trim().length > 0) {
                        p.innerHTML = text;
                    } else {
                        p.innerHTML = '<br>';
                    }
                    p.style.order = order;
                    p.style.color = 'red';
                    p.className = 'canvas-extracted-result';
                    document.getElementById('article').append(p);
                }
                CanvasRenderingContext2D.prototype.fillText = function(text, x, y, z) {
                    createP(this.canvas.style.order, text);
                    this.__oldFillText(text, x, y, z);
                }
                """;
        var webDriver = context.getWebDriver();
        if (webDriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) webDriver).executeScript(script);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
        WaitUtils.forInvisibility(webDriver, By.className("mx-auto"), 10);
        return null;
    }
}
