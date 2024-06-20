package org.lucycato.productcommandservice.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lucycato.common.SelfValidating;
import org.lucycato.productcommandservice.domain.enums.LectureCategory;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegisterLectureCommand extends SelfValidating<RegisterLectureCommand> {
    @NotNull
    private Long courseId;

    @NotBlank
    private String lectureTitle;

    @NotBlank
    private String lectureDescription;

    @NotNull
    private LectureCategory lectureCategory;

    private MultipartFile lectureThumbnailImageUrl;

    private MultipartFile lectureVideoUrl;

    public RegisterLectureCommand(Long courseId, String lectureTitle, String lectureDescription, LectureCategory lectureCategory, MultipartFile lectureThumbnailImageUrl, MultipartFile lectureVideoUrl) {
        this.courseId = courseId;
        this.lectureTitle = lectureTitle;
        this.lectureDescription = lectureDescription;
        this.lectureCategory = lectureCategory;
        this.lectureThumbnailImageUrl = lectureThumbnailImageUrl;
        this.lectureVideoUrl = lectureVideoUrl;

        this.validateSelf();
    }
}
