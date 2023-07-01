package com.webspider.infrastructure.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceCode {
    Metruyencv("metruyencv.com"),
    Truyenfull("truyenfull.vn");

    private final String code;
}
