package com.webspider.infrastructure.repository;

import com.webspider.application.model.request.StorySearchRequest;
import com.webspider.infrastructure.model.entity.JpaStory;
import com.webspider.infrastructure.model.enumeration.StoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface JpaStoryRepository extends JpaRepository<JpaStory, Long> {

    @Query("select o from JpaStory o" +
            " left join JpaCrawlStoryJob j on j.story.id = o.id and j.status in (com.webspider.infrastructure.model.enumeration.CrawlStoryJobStatus.Ready, com.webspider.infrastructure.model.enumeration.CrawlStoryJobStatus.InProgress)" +
            " where" +
            " j.id is null" +
            " and (" +
            "   o.status in (com.webspider.infrastructure.model.enumeration.StoryStatus.Draft) " +
            "   or (o.completed = false and o.lastModifiedDate < :lastModifiedDate)" +
            " )" +
            " order by o.createdDate desc")
    Slice<JpaStory> findStoriesWithoutActiveCrawlInfoJob(@Param("lastModifiedDate") Instant lastModifiedDate, Pageable pageable);

    @Query("select o from JpaStory o" +
            " left join JpaCrawlChaptersJob j on j.story.id = o.id and j.status in (com.webspider.infrastructure.model.enumeration.CrawlChaptersJobStatus.Ready, com.webspider.infrastructure.model.enumeration.CrawlChaptersJobStatus.InProgress)" +
            " where" +
            " j.id is null" +
            " and (" +
            "   o.status in (com.webspider.infrastructure.model.enumeration.StoryStatus.Draft) " +
            "   or (o.completed = false and o.lastModifiedDate < :lastModifiedDate)" +
            " )" +
            " order by o.createdDate desc")
    Slice<JpaStory> findStoriesWithoutActiveCrawlChaptersJob(@Param("lastModifiedDate") Instant lastModifiedDate, Pageable pageable);

    boolean existsBySlug(String slug);
    Optional<JpaStory> findBySlugAndStatus(String slug, StoryStatus status);

    @Query("select o from JpaStory o" +
            " where (:#{#request.keyword == null} = true or o.title like %:#{#request.keyword}%)")
    Page<JpaStory> search(@Param("request") StorySearchRequest request, Pageable pageable);

    Page<JpaStory> findAllByGenresCode(String genreCode, Pageable pageable);
    Page<JpaStory> findAllByAuthorCode(String authorCode, Pageable pageable);
}