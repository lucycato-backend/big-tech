package org.lucycato.usercoursequeryservice.adapter.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lucycato.usercoursequeryservice.domain.enums.CourseSeriesCategory;
import org.lucycato.usercoursequeryservice.domain.enums.CourseSeriesStatus;
import org.lucycato.usercoursequeryservice.domain.enums.SubjectCategory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("user_course_series")
@Getter
@Setter
@NoArgsConstructor
public class UserCourseSeriesR2dbcEntity {
    @Id
    private Long id;

    private Long teacherId;

    private String imageUrl;

    private String title;

    private String description;

    private List<String> explainImageUrls;

    private SubjectCategory subjectCategory;

    private CourseSeriesCategory category;

    private CourseSeriesStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}