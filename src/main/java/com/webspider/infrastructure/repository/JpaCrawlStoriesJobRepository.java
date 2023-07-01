package com.webspider.infrastructure.repository;

import com.webspider.infrastructure.model.entity.crawl.JpaCrawlStoriesJob;
import com.webspider.infrastructure.model.enumeration.CrawlStoriesJobStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaCrawlStoriesJobRepository extends JpaRepository<JpaCrawlStoriesJob, Long> {

    Slice<JpaCrawlStoriesJob> findAllByStatusOrderByCreatedDateAsc(CrawlStoriesJobStatus status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoriesJob o set o.status = com.webspider.infrastructure.model.enumeration.CrawlStoriesJobStatus.Ready" +
            " where o.status = com.webspider.infrastructure.model.enumeration.CrawlStoriesJobStatus.InProgress")
    void updateInProgressToReady();

    boolean existsByStatusIn(List<CrawlStoriesJobStatus> statuses);

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoriesJob o set o.status = :status where o.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") CrawlStoriesJobStatus status);

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoriesJob o set o.currentPage = :currentPage where o.id = :id")
    void setCurrentPage(@Param("id") Long id, @Param("currentPage") int currentPage);

}