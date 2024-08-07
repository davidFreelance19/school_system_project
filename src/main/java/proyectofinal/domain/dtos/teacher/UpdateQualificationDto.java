package proyectofinal.domain.dtos.teacher;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.teacher.ErrorsQualification;

public class UpdateQualificationDto {
    private Long id;
    private Long qualification;
    
    
    public UpdateQualificationDto(Long id, Long qualification) {
        this.id = id;
        this.qualification = qualification;
    }


    public Long getQualification() {
        return qualification;
    }


    public Long getId() {
        return id;
    }    

    public static ResultDto<UpdateQualificationDto> update(Optional<Map<String, Object>> props, Long qualificationId){
        if(!props.isPresent())
            return new ResultDto<UpdateQualificationDto>(ErrorsShared.BODY_EMPTY_ERROR, null);
        
        if(!props.get().containsKey("qualification"))
            return new ResultDto<UpdateQualificationDto>(ErrorsQualification.QUALIFICATION_REQUIRED, null);

        if(!(props.get().get("qualification") instanceof String))
            return new ResultDto<UpdateQualificationDto>(ErrorsQualification.QUALIFICATION_INVALID, null);
        
        Long qualification;
        
        try {
            qualification = Long.parseLong(props.get().get("qualification").toString());
        } catch (Exception e) {
            return new ResultDto<UpdateQualificationDto>(ErrorsQualification.QUALIFICATION_INVALID, null);
        }

        if(qualification < 5 || qualification > 10)
            return new ResultDto<UpdateQualificationDto>(ErrorsQualification.QUALIFICATION_INVALID, null);

        return new ResultDto<UpdateQualificationDto>(null, new UpdateQualificationDto(qualificationId, qualification));
    }
}
