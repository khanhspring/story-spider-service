package com.webspider.service.author;

import com.webspider.infrastructure.model.entity.JpaAuthor;

public interface AuthorService {
    JpaAuthor findByNameOrElseCreate(String name);
}
