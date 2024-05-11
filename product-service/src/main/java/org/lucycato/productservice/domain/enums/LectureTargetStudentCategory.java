package org.lucycato.productservice.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LectureTargetStudentCategory {
    STUDENT("학생"),
    NEWCOMER("신입"),
    YEAR1_TO_YEAR3("1년차 ~ 3년차")
    ;

    private final String description;
}
