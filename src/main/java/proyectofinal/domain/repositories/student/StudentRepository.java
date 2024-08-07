package proyectofinal.domain.repositories.student;

import java.util.Map;

import proyectofinal.utils.http.ExceptionHandler;

public abstract class StudentRepository {

    protected abstract Map<String, Object> getQualificationsStudent(Long studentId) throws ExceptionHandler;

}
