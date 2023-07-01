package com.webspider.service.storyview;

import java.time.LocalDate;

public interface StoryViewService {
    void increaseView(Long storyId);
    void initView(Long storyId, int value, LocalDate statsDate);
}
