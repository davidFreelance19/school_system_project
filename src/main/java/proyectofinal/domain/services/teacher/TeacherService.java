package proyectofinal.domain.services.teacher;

import java.util.Map;

import proyectofinal.domain.dtos.teacher.RegisterQualificationDto;
import proyectofinal.domain.dtos.teacher.UpdateQualificationDto;
import proyectofinal.utils.http.ExceptionHandler;

public abstract class TeacherService {

    protected abstract Map<String, Object> getSubjectsToTeacher(Long teacherId) throws ExceptionHandler;

    protected abstract Map<String, Object> getSubjectById(Long subjectId) throws ExceptionHandler;

    protected abstract Map<String, Object> registerQualification(RegisterQualificationDto dto) throws ExceptionHandler;

    protected abstract Map<String, Object> updateQualification(UpdateQualificationDto dto) throws ExceptionHandler;

    protected abstract Map<String, Object> deleteQualification(Long qualificationId) throws ExceptionHandler;
}
