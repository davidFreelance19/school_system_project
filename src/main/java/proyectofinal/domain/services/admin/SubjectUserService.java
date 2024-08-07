package proyectofinal.domain.services.admin;

import java.util.Map;

import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.interfaces.SubjectUser;
import proyectofinal.utils.http.ExceptionHandler;

public abstract class SubjectUserService {
    protected abstract Map<String, SubjectUser> getStudentToSubject(GetUserToSubjectDto dto) throws ExceptionHandler;
}
