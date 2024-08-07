package proyectofinal.domain.services.student;

import java.util.Map;

import proyectofinal.utils.http.ExceptionHandler;

public abstract class StudentService {

    protected abstract Map<String, Object> getQualificationsStudent(Long studentId) throws ExceptionHandler;

}
