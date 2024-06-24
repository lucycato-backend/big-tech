package org.lucycato.productqueryservice.application.service;

import lombok.RequiredArgsConstructor;
import org.lucycato.productqueryservice.application.port.in.CourseSeriesUseCase;
import org.lucycato.productqueryservice.application.port.in.command.CourseSeriesDetailSearchCommand;
import org.lucycato.productqueryservice.application.port.in.command.SpecificCourseSeriesCourseSearchByTeacherIdCommand;
import org.lucycato.productqueryservice.application.port.in.command.SpecificCourseSeriesCourseSearchCommand;
import org.lucycato.productqueryservice.application.port.in.command.SpecificCourseSeriesTextEBookSearchCommand;
import org.lucycato.productqueryservice.application.port.out.CoursePort;
import org.lucycato.productqueryservice.application.port.out.CourseSeriesPort;
import org.lucycato.productqueryservice.application.port.out.TeacherPort;
import org.lucycato.productqueryservice.application.port.out.TextEBookPort;
import org.lucycato.productqueryservice.application.port.out.result.*;
import org.lucycato.productqueryservice.domain.CourseSeriesCourse;
import org.lucycato.productqueryservice.domain.CourseSeriesDetail;
import org.lucycato.productqueryservice.domain.CourseSeriesTextEBook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseSeriesService implements CourseSeriesUseCase {
    private final CourseSeriesPort courseSeriesPort;

    private final TeacherPort teacherPort;

    private final CoursePort coursePort;

    private final TextEBookPort textEBookPort;

    @Override
    public Mono<CourseSeriesDetail> getCourseSeries(CourseSeriesDetailSearchCommand command) {
        return courseSeriesPort.getCourseSeries(command.getCourseSeriesId())
                .map(CourseSeriesDetail::from);
    }

    //TODO: 성능 최적화
    @Override
    public Flux<CourseSeriesCourse> getCourseSeriesCourseList(SpecificCourseSeriesCourseSearchCommand command) {
        Map<Long, TextEBookResult> textEBookMap = new ConcurrentHashMap<>();
        Map<Long, CheckedRecentCourseOpenResult> checkRecentOpenCourseMap = new ConcurrentHashMap<>();

        Mono<Void> textEBookTask = textEBookPort.getTextEBookListByCourseSeriesIds(Collections.singletonList(command.getCourseSeriesId()))
                .flatMap(item -> {
                    textEBookMap.put(item.getCourseId(), item);
                    return Flux.empty();
                })
                .then();

        Mono<Void> checkRecentCourseOpenTask = coursePort.getCourseIdsByCourseSeriesId(command.getCourseSeriesId())
                .collectList()
                .flatMapMany(coursePort::checkRecentCourseOpenListByCourseIds)
                .flatMap(item -> {
                    checkRecentOpenCourseMap.put(item.getCourseId(), item);
                    return Flux.empty();
                })
                .then();

        return Flux.combineLatest(
                        Mono.when(textEBookTask, checkRecentCourseOpenTask).then(Mono.just("Complete")).flux(),
                        coursePort.getCourseListByCourseSeriesIds(Collections.singletonList(command.getCourseSeriesId())),
                        (a, b) -> b
                )
                .flatMap(courseResult -> Mono.zip(
                                        teacherPort.getSimpleTeacher(command.getCourseSeriesId()),
                                        courseSeriesPort.getSimpleCourseSeries(courseResult.getCourseId())
                                )
                                .cache()
                                .flatMapMany(tuples -> Flux.just(CourseSeriesCourse.from(
                                        courseResult,
                                        tuples.getT1(),
                                        tuples.getT2(),
                                        textEBookMap.get(courseResult.getCourseId()),
                                        Optional.ofNullable(checkRecentOpenCourseMap.get(courseResult.getCourseId()))
                                                .isPresent()
                                )))
                )
                .sort((a, b) -> Long.compare(b.getCourseId(), a.getCourseId()));


//        return Mono.when(process)
//                .flatMapMany(it -> coursePort.getCourseListByCourseSeriesId(command.getCourseSeriesId()))
//                .flatMap(courseResult -> Mono.zip(
//                                        teacherPort.getSimpleTeacher(command.getCourseSeriesId()),
//                                        courseSeriesPort.getSimpleCourseSeries(courseResult.getCourseId())
//                                ).cache()
//                                .flatMapMany(tuples -> Flux.just(CourseSeriesCourse.from(
//                                        courseResult,
//                                        tuples.getT1(),
//                                        tuples.getT2(),
//                                        map.get(courseResult.getCourseId())))
//                                )
//                );
    }

    //TODO: 성능 최적화
    @Override
    public Flux<CourseSeriesCourse> getCourseSeriesCourseListByTeacherId(SpecificCourseSeriesCourseSearchByTeacherIdCommand command) {
        Map<Long, CourseSeriesResult> courseSeriesMap = new ConcurrentHashMap<>();
        Map<Long, TextEBookResult> textEBookMap = new ConcurrentHashMap<>();
        Map<Long, CheckedRecentCourseOpenResult> checkRecentOpenCourseMap = new ConcurrentHashMap<>();

        Mono<Void> courseTask = courseSeriesPort.getCourseSeriesListByTeacherIds(Collections.singletonList(command.getTeacherId()))
                .flatMap(item -> {
                    courseSeriesMap.put(item.getTeacherId(), item);
                    return Flux.empty();
                })
                .then();

        Mono<Void> textEBookTask = textEBookPort.getTextEBookListByTeacherIds(Collections.singletonList(command.getTeacherId()))
                .flatMap(item -> {
                    textEBookMap.put(item.getCourseId(), item);
                    return Flux.empty();
                })
                .then();

        Mono<Void> checkRecentCourseOpenTask = coursePort.checkRecentCourseOpenListByTeacherIds(Collections.singletonList(command.getTeacherId()))
                .flatMap(item -> {
                    checkRecentOpenCourseMap.put(item.getCourseId(), item);
                    return Flux.empty();
                })
                .then();

        return Flux.combineLatest(
                        Mono.when(courseTask, textEBookTask, checkRecentCourseOpenTask).then(Mono.just("Complete")).flux(),
                        coursePort.getCourseListByTeacherIds(Collections.singletonList(command.getTeacherId())),
                        (a, b) -> b
                )
                .flatMap(courseResult -> teacherPort.getSimpleTeacher(command.getTeacherId())
                        .defaultIfEmpty(new TeacherResult())
                        .cache()
                        .flatMapMany(teacherResult -> Flux.just(CourseSeriesCourse.from(
                                courseResult,
                                teacherResult,
                                courseSeriesMap.get(courseResult.getTeacherId()) != null ? courseSeriesMap.get(courseResult.getTeacherId()) : new CourseSeriesResult(),
                                textEBookMap.get(courseResult.getCourseId()) != null ? textEBookMap.get(courseResult.getCourseId()) : new TextEBookResult(),
                                Optional.ofNullable(checkRecentOpenCourseMap.get(courseResult.getCourseId()))
                                        .isPresent()
                        )))
                )
                .sort((a, b) -> Long.compare(b.getCourseId(), a.getCourseId()));
    }

    @Override
    public Flux<CourseSeriesTextEBook> getCourseSeriesTextEBookList(SpecificCourseSeriesTextEBookSearchCommand command) {
        return Flux.combineLatest(
                        courseSeriesPort.getSimpleCourseSeries(command.getTeacherId()).flux(),
                        textEBookPort.getTextEBookListByTeacherIds(Collections.singletonList(command.getTeacherId())),
                        CourseSeriesTextEBook::from
                )
                .sort((a, b) -> Long.compare(b.getTextEBookId(), a.getTextEBookId()));
    }
}
