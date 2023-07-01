package com.webspider.infrastructure.repository;

import com.webspider.infrastructure.model.entity.crawl.JpaCrawlChaptersJob;
import com.webspider.infrastructure.model.enumeration.CrawlChaptersJobStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaCrawlChaptersJobRepository extends JpaRepository<JpaCrawlChaptersJob, Long> {


    Slice<JpaCrawlChaptersJob> findAllByStatusOrderByCreatedDateAsc(CrawlChaptersJobStatus status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update JpaCrawlChaptersJob o set o.status = com.webspider.infrastructure.model.enumeration.CrawlChaptersJobStatus.Ready" +
            " where o.status = com.webspider.infrastructure.model.enumeration.CrawlChaptersJobStatus.InProgress")
    void updateInProgressToReady();

    @Modifying
    @Transactional
    @Query("update JpaCrawlChaptersJob o set o.status = :status where o.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") CrawlChaptersJobStatus status);
}