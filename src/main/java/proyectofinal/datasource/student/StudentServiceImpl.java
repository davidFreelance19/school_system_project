package proyectofinal.datasource.student;

import java.util.Map;

import proyectofinal.domain.services.student.StudentService;
import proyectofinal.utils.http.ExceptionHandler;

public class StudentServiceImpl extends StudentService {

    private StudentRepositoryImpl repository;

    public StudentServiceImpl(StudentRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, Object> getQualificationsStudent(Long studentId) throws ExceptionHandler{
        return repository.getQualificationsStudent(studentId);
    }

}