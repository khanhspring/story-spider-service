package com.webspider.infrastructure.pool.webdriverpool;

import com.webspider.infrastructure.property.WebDriverPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StoryDetailWebDriverPool extends WebDriverGenericPool {

    public StoryDetailWebDriverPool(WebDriverPoolProperties properties) {
        super(
                properties.getStoryDetail().getMaxSize(),
                properties.getStoryDetail().isHeadless(),
                properties.getStoryDetail().getRemoteUrl()
        );
    }

}
