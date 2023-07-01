package com.webspider.infrastructure.model.entity.crawl;

import com.webspider.infrastructure.model.entity.JpaBaseEntity;
import com.webspider.infrastructure.model.entity.JpaStory;
import com.webspider.infrastructure.model.enumeration.CrawlStoriesJobStatus;
import com.webspider.infrastructure.model.enumeration.CrawlStoryJobStatus;
import com.webspider.infrastructure.model.enumeration.CrawlStoryJobType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crawl_story_job")
public class JpaCrawlStoryJob extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "story_id")
    private JpaStory story;

    @Enumerated(EnumType.STRING)
    private CrawlStoryJobType type;
    private int priority;
    private String url;

    @Enumerated(EnumType.STRING)
    private CrawlStoryJobStatus status;

    private Instant completedTime;
    private String errorMessage;
    private int failedCount;
}
