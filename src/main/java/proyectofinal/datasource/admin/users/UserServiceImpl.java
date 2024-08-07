package proyectofinal.datasource.admin.users;

import java.util.Map;

import proyectofinal.domain.dtos.admin.user.CreateUserDto;
import proyectofinal.domain.dtos.admin.user.UpdateUserDto;
import proyectofinal.domain.interfaces.User;
import proyectofinal.domain.services.admin.UserService;
import proyectofinal.utils.http.ExceptionHandler;

public class UserServiceImpl extends UserService {

    private UserRepositoryImpl directivoRepository;

    public UserServiceImpl(UserRepositoryImpl directivoRepository) {
        this.directivoRepository = directivoRepository;
    }

    @Override
    public Map<String, Object> registerUser(CreateUserDto createUserDto, String ROLE) throws ExceptionHandler {
        return directivoRepository.registerUser(createUserDto, ROLE);
    }

    @Override
    public Map<String, Object> getUsersByRole(String typeUser) throws ExceptionHandler {
        return directivoRepository.getUsersByRole(typeUser);
    }

    @Override
    public Map<String, User> getUserByIdAndRole(Long id, String typeUser) throws ExceptionHandler {
        return directivoRepository.getUserByIdAndRole(id, typeUser);
    }

    @Override
    public Map<String, User> getUserByFullNameAndRole(String fullname, String typeUser) throws ExceptionHandler {
        return directivoRepository.getUserByFullNameAndRole(fullname, typeUser);
    }

    
    @Override
    public Map<String, Object> updateUser(UpdateUserDto dto, String typeUser) throws ExceptionHandler {
        return directivoRepository.updateUser(dto, typeUser);
    }

    @Override
    public Map<String, Object> deleteUser(Long id, String typeUser) throws ExceptionHandler {
        return directivoRepository.deleteUser(id, typeUser);
    }

}