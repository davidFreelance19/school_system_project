package proyectofinal.domain.dtos.admin.subject;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;

public class RegisterSubjectDto {
    private String name;
    private String groupName;
    private String teacherFullName;

    public RegisterSubjectDto(String name, String groupName) {
        this.name = name;
        this.groupName = groupName;
    }

    public RegisterSubjectDto(String name, String groupName, String teacherFullName) {
        this.name = name;
        this.groupName = groupName;
        this.teacherFullName = teacherFullName;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public static ResultDto<RegisterSubjectDto> validate(Optional<Map<String, Object>> props) {
        if (!props.isPresent())
            return new ResultDto<RegisterSubjectDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (!props.get().containsKey("name"))
            return new ResultDto<RegisterSubjectDto>(ErrorsSubject.SUBJECT_NAME_REQUIRED, null);

        if (!(props.get().get("name") instanceof String))
            return new ResultDto<RegisterSubjectDto>(ErrorsSubject.SUBJECT_NAME_INVALID, null);

        if (!props.get().containsKey("groupName"))
            return new ResultDto<RegisterSubjectDto>(ErrorsSubject.GROUP_NAME_REQUIRED, null);

        if (!(props.get().get("groupName") instanceof String))
            return new ResultDto<RegisterSubjectDto>(ErrorsSubject.GROUP_NAME_INVALID, null);
        
        String name = props.get().get("name").toString().trim();
        String groupName = props.get().get("groupName").toString().trim();

        if(!props.get().containsKey("teacherFullName"))
            return new ResultDto<RegisterSubjectDto>(null, new RegisterSubjectDto(name, groupName));
        
        if(!(props.get().get("teacherFullName") instanceof String))
            return new ResultDto<RegisterSubjectDto>(ErrorsSubject.TEACHER_NAME_INVALID, null);
        
        String teacherFullName = props.get().get("teacherFullName").toString().trim();
        return new ResultDto<RegisterSubjectDto>(null, new RegisterSubjectDto(name, groupName, teacherFullName));
    }
}
