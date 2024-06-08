package org.lucycato.usercoursequeryservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.lucycato.usercoursequeryservice.domain.enums.CourseSeriesCategory;
import org.lucycato.usercoursequeryservice.domain.enums.CourseSeriesStatus;
import org.lucycato.usercoursequeryservice.domain.enums.SubjectCategory;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseSeries {
    private final Long courseSeriesId;

    private final Long teacherId;

    private final String courseSeriesImageUrl;

    private final String courseSeriesTitle;

    private final SubjectCategory subjectCategory;

    private final CourseSeriesCategory courseSeriesCategory;

    private final CourseSeriesStatus courseSeriesStatus;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;
}