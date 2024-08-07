package proyectofinal.domain.repositories.auth;

import java.util.Map;

import proyectofinal.domain.dtos.auth.LoginDto;
import proyectofinal.utils.http.ExceptionHandler;

public abstract class AuthRepository {

    protected abstract Map<String, Object> login(LoginDto dto) throws ExceptionHandler;
}
