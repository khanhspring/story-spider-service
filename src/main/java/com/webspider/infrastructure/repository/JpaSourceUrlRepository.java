package com.webspider.infrastructure.repository;

import com.webspider.infrastructure.model.entity.crawl.JpaSourceUrl;
import com.webspider.infrastructure.model.enumeration.SourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSourceUrlRepository extends JpaRepository<JpaSourceUrl, Long> {

    List<JpaSourceUrl> findAllBySourceStatus(SourceStatus status);
}