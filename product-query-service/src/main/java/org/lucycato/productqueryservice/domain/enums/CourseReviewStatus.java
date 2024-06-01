package org.lucycato.productqueryservice.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CourseReviewStatus {
    REGISTERED("등록"),
    UNREGISTERED("등록 해지"),

    ;
    private final String description;
}