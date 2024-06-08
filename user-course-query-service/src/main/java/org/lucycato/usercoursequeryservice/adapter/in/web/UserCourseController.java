package org.lucycato.usercoursequeryservice.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.lucycato.common.annotation.hexagonal.in.WebAdapter;
import org.lucycato.usercoursequeryservice.application.port.in.CourseUseCase;
import org.lucycato.usercoursequeryservice.application.port.in.command.*;
import org.lucycato.usercoursequeryservice.domain.*;
import org.lucycato.usercoursequeryservice.domain.enums.CourseGenre;
import org.lucycato.usercoursequeryservice.domain.enums.SubjectCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class UserCourseController {
    private final CourseUseCase courseUseCase;
    /*
    토론: status query parameter 공개 하는것은 타당한가
    토론: WebFlux 사용은 write 사용에 안정적이지 않은 것인가
     */

    @GetMapping("open-api/usercourse/v1/courses")
    public Flux<Course> getCourses(
            @RequestParam(name = "courseGenre", required = false)
            CourseGenre courseGenre,
            @RequestParam(name = "subjectCategory", required = false)
            SubjectCategory subjectCategory
    ) {
        CourseSearchCommand command = new CourseSearchCommand(
                courseGenre,
                subjectCategory
        );
        return courseUseCase.getCurses(command);
    }

    @GetMapping("open-api/usercourse/v1/courses/{courseId}")
    public Mono<CourseDetail> getCourse(
            @PathVariable
            Long courseId
    ) {
        CourseDetailSearchCommand command = new CourseDetailSearchCommand(
                courseId
        );
        return courseUseCase.getCures(command);
    }

    @GetMapping("open-api/usercourse/v1/courses/{courseId}/lectures")
    public Flux<CourseLecture> getCourseLectures(
            @PathVariable
            Long courseId
    ) {
        SpecificCourseLectureSearchCommand command = new SpecificCourseLectureSearchCommand(
                courseId
        );
        return courseUseCase.getCourseLectures(command);
    }

    @GetMapping("open-api/usercourse/v1/courses/{courseId}/text-e-books")
    public Flux<CourseTextEBook> getCourseTextEBooks(
            @PathVariable
            Long courseId
    ) {
        SpecificCourseTextEBookSearchCommand command = new SpecificCourseTextEBookSearchCommand(
                courseId
        );
        return courseUseCase.getCourseTextEBooks(command);
    }

    @GetMapping("open-api/usercourse/v1/courses/{courseId}/reviews")
    public Flux<CourseReview> getCourseReviews(
            @PathVariable
            Long courseId
    ) {
        SpecificCourseReviewSearchCommand command = new SpecificCourseReviewSearchCommand(
                courseId
        );
        return courseUseCase.getCourseReviews(command);
    }

    @GetMapping("open-api/usercourse/v1/courses/by-teacher/{teacherId}/reviews")
    public Flux<CourseReview> getCourseReviewsByTeacher(
            @PathVariable
            Long teacherId
    ) {
        SpecificCourseReviewByTeacherSearchCommand command = new SpecificCourseReviewByTeacherSearchCommand(
                teacherId
        );
        return courseUseCase.getCourseReviews(command);
    }
}