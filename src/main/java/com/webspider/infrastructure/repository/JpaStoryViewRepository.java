package com.webspider.infrastructure.repository;

import com.webspider.infrastructure.model.entity.JpaStoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaStoryViewRepository extends JpaRepository<JpaStoryView, Long> {

    @Modifying
    @Query(value = "insert into story_view (story_id, view_count, stats_date, created_date, version)" +
            " values (:storyId, 1, :statsDate, CURRENT_TIMESTAMP, 0)" +
            " on conflict (story_id, stats_date)" +
            " do update set" +
            " view_count = story_view.view_count + 1," +
            " last_modified_date = CURRENT_TIMESTAMP",
            nativeQuery = true)
    void increaseView(@Param("storyId") Long storyId, @Param("statsDate") LocalDate statsDate);

    Optional<JpaStoryView> findAllByStoryIdAndStatsDate(Long storyId, LocalDate statsDate);
}