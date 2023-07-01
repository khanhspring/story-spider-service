package com.webspider.application.api.external;

import com.webspider.application.model.request.GenreSearchRequest;
import com.webspider.application.model.response.GenreResponse;
import com.webspider.application.model.response.StoryResponse;
import com.webspider.service.external.GenreExternalService;
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
@RequestMapping("genres")
public class GenreExternalController {
    private final StoryExternalService storyExternalService;
    private final GenreExternalService genreExternalService;

    @GetMapping
    public Page<GenreResponse> search(GenreSearchRequest request,
                                      @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return genreExternalService.search(request, pageable);
    }

    @GetMapping("{code}")
    public GenreResponse findByCode(@PathVariable String code) {
        return genreExternalService.findByCode(code);
    }

    @GetMapping("{code}/stories")
    public Page<StoryResponse> searchStory(@PathVariable String code,
                                           @PageableDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return storyExternalService.findByGenre(code, pageable);
    }
}
