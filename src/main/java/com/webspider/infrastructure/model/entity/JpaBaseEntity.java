package com.webspider.infrastructure.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class JpaBaseEntity extends JpaTimestampEntity {
    @Version
    private Long version;
}
