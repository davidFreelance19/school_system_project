package proyectofinal.domain.dtos.teacher;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.teacher.ErrorsQualification;

public class RegisterQualificationDto {
    
    private GetUserToSubjectDto getUserToSubject;
    private Long qualification;
    
    
    
    public RegisterQualificationDto(GetUserToSubjectDto getUserToSubject, Long qualification) {
        this.getUserToSubject = getUserToSubject;
        this.qualification = qualification;
    }

    public GetUserToSubjectDto getGetUserToSubject() {
        return getUserToSubject;
    }

    public Long getQualification() {
        return qualification;
    }

    public static ResultDto<RegisterQualificationDto> validate(Optional<Map<String, Object>> props, Long subjectId){
        if(!props.isPresent())
            return new ResultDto<RegisterQualificationDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if(!props.get().containsKey("studentId"))
            return new ResultDto<RegisterQualificationDto>(ErrorsQualification.STUDENT_ID_REQUIRED, null);

        if(!props.get().containsKey("qualification"))
            return new ResultDto<RegisterQualificationDto>(ErrorsQualification.QUALIFICATION_REQUIRED, null);

        if(!(props.get().get("studentId") instanceof String))
            return new ResultDto<RegisterQualificationDto>(ErrorsQualification.STUDENT_ID_INVALID, null);

        if(!(props.get().get("qualification") instanceof String))
            return new ResultDto<RegisterQualificationDto>(ErrorsQualification.QUALIFICATION_INVALID, null);
        
        Long qualification = null;

        try {
            qualification = Long.parseLong(props.get().get("qualification").toString());
            if(qualification < 5 || qualification > 10)
                return new ResultDto<RegisterQualificationDto>(ErrorsQualification.QUALIFICATION_INVALID, null);
        } catch (Exception e) {
            return new ResultDto<RegisterQualificationDto>(ErrorsQualification.QUALIFICATION_INVALID, null);
        }

        ResultDto<GetUserToSubjectDto> resultDto = GetUserToSubjectDto.validateParams(props.get().get("studentId").toString(), subjectId.toString());
 
        return new ResultDto<RegisterQualificationDto>(null, new RegisterQualificationDto(resultDto.getDto(), qualification));
    }
    
}
