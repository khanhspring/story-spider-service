package com.webspider.application.api.external;

import com.webspider.application.model.request.AuthorSearchRequest;
import com.webspider.application.model.response.AuthorResponse;
import com.webspider.application.model.response.StoryResponse;
import com.webspider.service.external.AuthorExternalService;
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
@RequestMapping("authors")
public class AuthorExternalController {
    private final StoryExternalService storyExternalService;
    private final AuthorExternalService authorExternalService;

    @GetMapping
    public Page<AuthorResponse> search(AuthorSearchRequest request,
                                       @PageableDefault(sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        return authorExternalService.search(request, pageable);
    }

    @GetMapping("{code}")
    public AuthorResponse findByCode(@PathVariable String code) {
        return authorExternalService.findByCode(code);
    }

    @GetMapping("{code}/stories")
    public Page<StoryResponse> searchStory(@PathVariable String code,
                                           @PageableDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return storyExternalService.findByAuthor(code, pageable);
    }
}
