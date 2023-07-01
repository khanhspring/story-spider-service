package com.webspider.infrastructure.converter;

import com.webspider.core.model.PageResult;
import com.webspider.core.model.StoriesResult;
import com.webspider.core.model.StoryResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class StoriesResultConverter {

    public StoriesResult convert(PageResult<List<Map<String, String>>> pageResult, int pageNumber, Function<String, String> slugExtractor) {
        var rawStories = pageResult.getContent();
        List<StoryResult> stories = new ArrayList<>();
        for (var rawStory : rawStories) {
            var url = rawStory.get("story_url");
            var story = StoryResult.builder()
                    .title(rawStory.get("story_title"))
                    .url(url)
                    .slug(slugExtractor.apply(url))
                    .build();
            stories.add(story);
        }
        return StoriesResult.builder()
                .stories(stories)
                .pageNumber(pageNumber)
                .build();
    }
}
