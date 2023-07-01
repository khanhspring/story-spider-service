package com.webspider.service.storyview;

import com.webspider.infrastructure.model.entity.JpaStoryView;
import com.webspider.infrastructure.repository.JpaStoryRepository;
import com.webspider.infrastructure.repository.JpaStoryViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoryViewServiceImpl implements StoryViewService {
    private final JpaStoryViewRepository jpaStoryViewRepository;
    private final JpaStoryRepository jpaStoryRepository;

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseView(Long storyId) {
        jpaStoryViewRepository.increaseView(storyId, LocalDate.now());
    }

    @Override
    @Transactional
    public void initView(Long storyId, int value, LocalDate statsDate) {
        var viewToday = jpaStoryViewRepository.findAllByStoryIdAndStatsDate(storyId, statsDate)
                .orElse(null);

        if (Objects.isNull(viewToday)) {
            var story = jpaStoryRepository.findById(storyId)
                    .orElseThrow();
            viewToday = JpaStoryView.builder()
                    .statsDate(statsDate)
                    .story(story)
                    .build();
        }
        viewToday.setViewCount(value);
        jpaStoryViewRepository.save(viewToday);
    }
}
