package com.webspider.service.tag;

import com.webspider.infrastructure.model.entity.JpaTag;

public interface TagService {
    JpaTag findByTitleOrElseCreate(String title);
}
