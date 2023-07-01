package com.webspider.application.api.external;

import com.webspider.application.model.request.StorySearchRequest;
import com.webspider.application.model.response.ChapterResponse;
import com.webspider.application.model.response.ChapterSimpleResponse;
import com.webspider.application.model.response.StoryResponse;
import com.webspider.service.external.ChapterExternalService;
import com.webspider.service.external.StoryExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("stories")
public class StoryExternalController {
    private final StoryExternalService storyExternalService;
    private final ChapterExternalService chapterExternalService;

    @GetMapping
    public Page<StoryResponse> search(StorySearchRequest request,
                                      @PageableDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return storyExternalService.search(request, pageable);
    }

    @GetMapping("{slug}")
    public StoryResponse findBySlug(@PathVariable String slug) {
        return storyExternalService.findBySlug(slug);
    }

    @GetMapping("{storySlug}/chapters")
    public Page<ChapterSimpleResponse> getChapters(@PathVariable String storySlug,
                                                   @PageableDefault(sort = "index", direction = Sort.Direction.ASC) Pageable pageable) {
        return chapterExternalService.findByStory(storySlug, pageable);
    }

    @GetMapping("{storySlug}/chapters/{chapterIndex}")
    public ChapterResponse getChapter(@PathVariable String storySlug, @PathVariable int chapterIndex) {
        return chapterExternalService.findByStorySlugAndIndex(storySlug, chapterIndex);
    }
}
