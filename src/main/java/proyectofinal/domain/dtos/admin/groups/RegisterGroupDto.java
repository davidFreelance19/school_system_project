package proyectofinal.domain.dtos.admin.groups;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsGroup;

public class RegisterGroupDto {

    private String name;

    public RegisterGroupDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ResultDto<RegisterGroupDto> validate(Optional<Map<String, Object>> props) {

        if (!props.isPresent())
            return new ResultDto<RegisterGroupDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (props.get().get("name") == null)
            return new ResultDto<RegisterGroupDto>(ErrorsGroup.NAME_REQUIRED, null);

        if (!(props.get().get("name") instanceof String))
            return new ResultDto<RegisterGroupDto>(ErrorsGroup.NAME_INVALID, null);

        String name = (String) props.get().get("name");

        return new ResultDto<RegisterGroupDto>(null, new RegisterGroupDto(name));
    }

}
