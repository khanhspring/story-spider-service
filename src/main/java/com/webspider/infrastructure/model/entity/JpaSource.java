package com.webspider.infrastructure.model.entity;

import com.webspider.infrastructure.model.enumeration.SourceCode;
import com.webspider.infrastructure.model.enumeration.SourceStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "source")
public class JpaSource extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SourceCode code;

    private String name;
    private String url;

    @Enumerated(EnumType.STRING)
    private SourceStatus status;
}
