package proyectofinal.datasource.admin.subject_user;

import java.util.Map;

import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.interfaces.SubjectUser;
import proyectofinal.domain.services.admin.SubjectUserService;
import proyectofinal.utils.http.ExceptionHandler;

public class SubjectUserServiceImpl extends SubjectUserService{
    private SubjectUserRepositoryImpl repository;
    
    public SubjectUserServiceImpl(SubjectUserRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, SubjectUser> getStudentToSubject(GetUserToSubjectDto dto) throws ExceptionHandler {
        return repository.getStudentToSubject(dto);
    }
    
}
