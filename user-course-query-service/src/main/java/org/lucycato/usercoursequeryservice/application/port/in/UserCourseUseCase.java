package org.lucycato.usercoursequeryservice.application.port.in;

import org.lucycato.usercoursequeryservice.application.port.in.command.*;
import org.lucycato.usercoursequeryservice.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseUseCase {
    Flux<Course> getCurses(CourseSearchCommand command);

    Mono<CourseDetail> getCures(CourseDetailSearchCommand command);

    Flux<CourseLecture> getCourseLectures(SpecificCourseLectureSearchCommand command);

    Flux<CourseTextEBook> getCourseTextEBooks(SpecificCourseTextEBookSearchCommand command);

    Flux<CourseReview> getCourseReviews(SpecificCourseReviewSearchCommand command);

    Flux<CourseReview> getCourseReviews(SpecificCourseReviewByTeacherSearchCommand command);
}