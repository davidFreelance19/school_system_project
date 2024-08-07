package proyectofinal.domain.dtos.admin.subject;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;

public class RegisterStudentDto {
    private String fullname;
    private Long subjectId;

    public RegisterStudentDto(String fullname, Long subjectId) {
        this.subjectId = subjectId;
        this.fullname = fullname;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public String getFullname() {
        return fullname;
    }

    public static ResultDto<RegisterStudentDto> validate(Optional<Map<String, Object>> props, Long subjectId) {
        if (!props.isPresent())
            return new ResultDto<RegisterStudentDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (!props.get().containsKey("fullname"))
            return new ResultDto<RegisterStudentDto>(ErrorsSubject.STUDENTNAME_REQUIRED, null);

        if (!(props.get().get("fullname") instanceof String))
            return new ResultDto<RegisterStudentDto>(ErrorsSubject.STUDENTNAME_INVALID, null);

        String studentFullname = (String) props.get().get("fullname");

        return new ResultDto<RegisterStudentDto>(null, new RegisterStudentDto(studentFullname, subjectId));
    }
}
