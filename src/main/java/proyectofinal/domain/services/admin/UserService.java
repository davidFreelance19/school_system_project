package proyectofinal.domain.services.admin;

import java.util.Map;

import proyectofinal.domain.dtos.admin.user.CreateUserDto;
import proyectofinal.domain.dtos.admin.user.UpdateUserDto;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.http.ExceptionHandler;

public abstract class UserService {

    protected abstract Map<String, Object> registerUser(CreateUserDto dto, String ROLE) throws ExceptionHandler;

    protected abstract Map<String, Object> getUsersByRole(String typeUser) throws ExceptionHandler;

    protected abstract Map<String, User> getUserByIdAndRole(Long id, String typeUser) throws ExceptionHandler;

    protected abstract Map<String, User> getUserByFullNameAndRole(String fullname, String typeUser) throws ExceptionHandler;

    protected abstract Map<String, Object> updateUser(UpdateUserDto dto, String typeUser) throws ExceptionHandler;
    
    protected abstract Map<String, Object> deleteUser(Long id, String typeUser) throws ExceptionHandler;
}
