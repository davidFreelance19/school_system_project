package proyectofinal.domain.dtos.admin.subject;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;

public class UpdateSubjectDto {

    private Long id;
    private String name;
    private String groupName;
    private String teacherFullName;

    public UpdateSubjectDto() {
    }

    public Long getId() {
        return id;
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

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public static ResultDto<UpdateSubjectDto> validate(Optional<Map<String, Object>> props, Long id) {
        UpdateSubjectDto updateSubjectDto = new UpdateSubjectDto();
        updateSubjectDto.setId(id);

        if (!props.isPresent())
            return new ResultDto<UpdateSubjectDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (!props.get().containsKey("name") && !props.get().containsKey("teacherFullName") && !props.get().containsKey("groupName"))
            return new ResultDto<UpdateSubjectDto>(null, updateSubjectDto);

        if (props.get().containsKey("name")) {

            if (!(props.get().get("name") instanceof String))
                return new ResultDto<UpdateSubjectDto>(ErrorsSubject.SUBJECT_NAME_INVALID, null);

            String name = props.get().get("name").toString().trim();

            if (name.length() == 0)
                return new ResultDto<UpdateSubjectDto>(ErrorsSubject.SUBJECT_NAME_INVALID, null);
            updateSubjectDto.setName(name);
        }

        if (props.get().containsKey("groupName")) {

            if (!(props.get().get("groupName") instanceof String))
                return new ResultDto<UpdateSubjectDto>(ErrorsSubject.GROUP_NAME_INVALID, null);

            String groupName = props.get().get("groupName").toString().trim();
            updateSubjectDto.setGroupName(groupName);
        }

        if (props.get().containsKey("teacherFullName")) {

            if (!(props.get().get("teacherFullName") instanceof String))
                return new ResultDto<UpdateSubjectDto>(ErrorsSubject.TEACHER_NAME_INVALID, null);

            String teacherFullName = props.get().get("teacherFullName").toString().trim();
            updateSubjectDto.setTeacherFullName(teacherFullName);
        }

        return new ResultDto<UpdateSubjectDto>(null, updateSubjectDto);
    }
}
