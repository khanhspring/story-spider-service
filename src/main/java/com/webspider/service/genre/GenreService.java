package com.webspider.service.genre;

import com.webspider.infrastructure.model.entity.JpaGenre;

public interface GenreService {
    JpaGenre findByTitleOrElseCreate(String title);
}
