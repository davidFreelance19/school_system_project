package proyectofinal.domain.dtos.auth;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.auth.ErrorsLogin;

public class LoginDto {
    private String boleta;
    private String password;

    public LoginDto() {
    }

    public LoginDto(String boleta, String password) {
        this.boleta = boleta;
        this.password = password;
    }

    public String getBoleta() {
        return boleta;
    }

    public String getPassword() {
        return password;
    }

    public static ResultDto<LoginDto> validate(Optional<Map<String, Object>> props) {
        if (!props.isPresent())
            return new ResultDto<LoginDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (props.get().get("boleta") == null)
            return new ResultDto<LoginDto>(ErrorsLogin.BOLETA_IS_REQUIRED, null);
        if (!(props.get().get("boleta") instanceof String))
            return new ResultDto<LoginDto>(ErrorsLogin.BOLETA_IS_NOT_VALID, null);

        if (props.get().get("password") == null)
            return new ResultDto<LoginDto>(ErrorsLogin.PASSWORD_IS_REQUIRED, null);
        if (!(props.get().get("password") instanceof String))
            return new ResultDto<LoginDto>(ErrorsLogin.PASSWORD_IS_NOT_VALID, null);

        String password = (String) props.get().get("password");
        String boleta = (String) props.get().get("boleta");

        return new ResultDto<LoginDto>(null, new LoginDto(boleta, password));
    }
}
