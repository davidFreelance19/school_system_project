package proyectofinal.datasource.admin.subjects;

import java.util.Map;

import proyectofinal.domain.dtos.admin.subject.*;
import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.interfaces.Subject;
import proyectofinal.domain.services.admin.SubjectService;
import proyectofinal.utils.http.ExceptionHandler;

public class SubjectServiceImpl extends SubjectService {

    private SubjectRepositoryImpl respository;

    public SubjectServiceImpl(SubjectRepositoryImpl respository) {
        this.respository = respository;
    }
    
    @Override
    public Map<String, Object> getSubjects() throws ExceptionHandler {
        return respository.getSubjects();
    }

    @Override
    public Map<String, Subject> getSubjectById(Long id) throws ExceptionHandler {
        return respository.getSubjectById(id);
    }

    @Override
    public Map<String, Subject> getSubjectByName(String name) throws ExceptionHandler {
        return respository.getSubjectByName(name);
    }

    @Override
    public Map<String, String> registerSubject(RegisterSubjectDto dto) throws ExceptionHandler {
        return respository.registerSubject(dto);
    }

    @Override
    public Map<String, String> updateSubject(UpdateSubjectDto dto) throws ExceptionHandler {
        return respository.updateSubject(dto);
    }    

    @Override
    public Map<String, String> registerStudentToSubject(RegisterStudentDto dto) throws ExceptionHandler {
        return respository.registerStudentToSubject(dto);
    }

    @Override
    public Map<String, String> deleteStudentToSubject(GetUserToSubjectDto dto) throws ExceptionHandler {
        return respository.deleteStudentToSubject(dto);
    }    

    @Override
    public Map<String, String> deleteTeacherToSubject(GetUserToSubjectDto dto) throws ExceptionHandler {
        return respository.deleteTeacherToSubject(dto);
    }

    @Override
    public Map<String, String> deleteSubject(Long id) throws ExceptionHandler {
        return respository.deleteSubject(id);
    }

}
