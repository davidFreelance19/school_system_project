package proyectofinal.domain.repositories.admin;

import java.util.Map;

import proyectofinal.domain.dtos.admin.subject.*;
import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.interfaces.Subject;
import proyectofinal.utils.http.ExceptionHandler;

public abstract class SubjectRepository {

    protected abstract Map<String, Object> getSubjects() throws ExceptionHandler;

    protected abstract Map<String, Subject> getSubjectById(Long id) throws ExceptionHandler;

    protected abstract Map<String, Subject> getSubjectByName(String name) throws ExceptionHandler;
    
    protected abstract Map<String, String> registerSubject(RegisterSubjectDto dto) throws ExceptionHandler;

    protected abstract Map<String, String> updateSubject(UpdateSubjectDto dto) throws ExceptionHandler;

    protected abstract Map<String, String> registerStudentToSubject(RegisterStudentDto dto) throws ExceptionHandler;

    protected abstract Map<String, String> deleteStudentToSubject(GetUserToSubjectDto dto) throws ExceptionHandler;

    protected abstract Map<String, String> deleteTeacherToSubject(GetUserToSubjectDto dto) throws ExceptionHandler;

    protected abstract Map<String, String> deleteSubject(Long id) throws ExceptionHandler;
}
