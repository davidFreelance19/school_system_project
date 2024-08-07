package proyectofinal.datasource.teacher;

import java.util.Map;

import proyectofinal.domain.dtos.teacher.RegisterQualificationDto;
import proyectofinal.domain.dtos.teacher.UpdateQualificationDto;
import proyectofinal.domain.services.teacher.TeacherService;
import proyectofinal.utils.http.ExceptionHandler;

public class TeacherServiceImpl extends TeacherService {

    private TeacherRepositoryImpl repository;

    public TeacherServiceImpl(TeacherRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, Object> getSubjectsToTeacher(Long teacherId) throws ExceptionHandler{
        return repository.getSubjectsToTeacher(teacherId);
    }

    @Override
    public Map<String, Object> getSubjectById(Long subjectId) throws ExceptionHandler {
        return repository.getSubjectById(subjectId);
    }

    @Override 
    public Map<String, Object> registerQualification(RegisterQualificationDto dto) throws ExceptionHandler{
        return repository.registerQualification(dto);
    }

    @Override
    public Map<String, Object> updateQualification(UpdateQualificationDto dto) throws ExceptionHandler {
        return repository.updateQualification(dto);
    }

    @Override
    public Map<String, Object> deleteQualification(Long qualificationId) throws ExceptionHandler {
        return repository.deleteQualification(qualificationId);
    }
}
