package proyectofinal.domain.dtos.admin.subject_user;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;

public class GetUserToSubjectDto {

    private Long userId;
    private Long subjectId;

    public GetUserToSubjectDto(Long userId, Long subjectId) {
        this.userId = userId;
        this.subjectId = subjectId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public static ResultDto<GetUserToSubjectDto> validateParams(String userId, String subjectId) {
        Long userIdL;
        Long subjectIdL;

        if (userId == null)
            return new ResultDto<GetUserToSubjectDto>(ErrorsSubject.USER_ID_REQUIRED, null);

        if (subjectId == null)
            return new ResultDto<GetUserToSubjectDto>(ErrorsSubject.SUBJECT_ID_REQUIRED, null);

        try {
            userIdL = Long.parseLong(userId);
            subjectIdL = Long.parseLong(subjectId);
        } catch (Exception e) {
            return new ResultDto<GetUserToSubjectDto>(ErrorsSubject.QUERY_PARAMS_INVALID, null);
        }

        return new ResultDto<GetUserToSubjectDto>(null, new GetUserToSubjectDto(userIdL, subjectIdL));
    }
}
