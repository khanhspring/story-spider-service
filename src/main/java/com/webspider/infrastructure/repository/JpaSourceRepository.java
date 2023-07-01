package com.webspider.infrastructure.repository;

import com.webspider.infrastructure.model.entity.JpaSource;
import com.webspider.infrastructure.model.enumeration.SourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaSourceRepository extends JpaRepository<JpaSource, Long> {
    Optional<JpaSource> findByCode(String code);
    List<JpaSource> findByStatus(SourceStatus status);
}