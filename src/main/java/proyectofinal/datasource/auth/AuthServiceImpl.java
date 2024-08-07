package proyectofinal.datasource.auth;

import java.util.Map;

import proyectofinal.domain.dtos.auth.LoginDto;
import proyectofinal.domain.services.auth.AuthService;
import proyectofinal.utils.http.ExceptionHandler;

public class AuthServiceImpl extends AuthService {

    private AuthRepositoryImpl repository;

    public AuthServiceImpl(AuthRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, Object> login(LoginDto dto) throws ExceptionHandler {
        return repository.login(dto);
    }

}
