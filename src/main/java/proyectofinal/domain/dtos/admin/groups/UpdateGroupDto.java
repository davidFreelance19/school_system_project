package proyectofinal.domain.dtos.admin.groups;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsGroup;

public class UpdateGroupDto {
    private Long id;
    private String name;

    public UpdateGroupDto() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public static ResultDto<UpdateGroupDto> update(Optional<Map<String, Object>> props, Long id) {
        UpdateGroupDto group = new UpdateGroupDto();
        group.setId(id);

        if (!props.isPresent())
            return new ResultDto<UpdateGroupDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (!props.get().containsKey("name"))
            return new ResultDto<UpdateGroupDto>(null, group);

        if (!(props.get().get("name") instanceof String))
            return new ResultDto<UpdateGroupDto>(ErrorsGroup.NAME_INVALID, null);

        String name = props.get().get("name").toString().trim();
        group.setName(name);
        return new ResultDto<UpdateGroupDto>(null, group);
    };
}
