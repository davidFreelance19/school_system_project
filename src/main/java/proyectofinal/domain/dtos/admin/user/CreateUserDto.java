package proyectofinal.domain.dtos.admin.user;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsUser;

public class CreateUserDto {

    private String fullname;
    private String email;
    private String password;

    public CreateUserDto() {
    }

    public CreateUserDto(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static ResultDto<CreateUserDto> create(Optional<Map<String, Object>> props) {

        if (!props.isPresent())
            return new ResultDto<CreateUserDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (props.get().get("fullname") == null)
            return new ResultDto<CreateUserDto>(ErrorsUser.NAME_IS_REQUIRED, null);

        if (!(props.get().get("fullname") instanceof String))
            return new ResultDto<CreateUserDto>(ErrorsUser.NAME_NOT_VALID, null);

        if (props.get().get("email") == null)
            return new ResultDto<CreateUserDto>(ErrorsUser.EMAIL_IS_REQUIRED, null);

        if (!(props.get().get("email") instanceof String))
            return new ResultDto<CreateUserDto>(ErrorsUser.EMAIL_NOT_VALID, null);

        String fullname = (String) props.get().get("fullname");
        String email = (String) props.get().get("email");

        return new ResultDto<CreateUserDto>(null, new CreateUserDto(fullname, email));
    }

}
