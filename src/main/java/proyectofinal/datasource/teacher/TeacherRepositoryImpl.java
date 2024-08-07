package proyectofinal.datasource.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.datasource.admin.subject_user.SubjectUserServiceImpl;
import proyectofinal.domain.dtos.teacher.RegisterQualificationDto;
import proyectofinal.domain.dtos.teacher.UpdateQualificationDto;
import proyectofinal.domain.interfaces.Qualification;
import proyectofinal.domain.interfaces.SubjectUser;
import proyectofinal.domain.interfaces.teacher.StudentsInSubject;
import proyectofinal.domain.interfaces.teacher.SubjectToTeacher;
import proyectofinal.domain.repositories.teacher.TeacherRepository;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;
import proyectofinal.utils.http.errors.teacher.ErrorsQualification;
import proyectofinal.utils.http.status.HttpStatus;

public class TeacherRepositoryImpl extends TeacherRepository {

    private SubjectUserServiceImpl subjectUserService;
    
    public TeacherRepositoryImpl(SubjectUserServiceImpl subjectUserService) {
        this.subjectUserService = subjectUserService;
    }

    @Override
    public Map<String, Object> getSubjectsToTeacher(Long teacherId) throws ExceptionHandler {
        List<SubjectToTeacher> subjects = new ArrayList<SubjectToTeacher>();

        final String SQL = """
                     SELECT
                         subjects.id AS subject_id,
                         groups.name AS group_name,
                         subjects.name AS subject_name
                     FROM subjects
                     JOIN groups ON groups.id = subjects.group_id
                     JOIN subject_teacher ON subject_teacher.subject_id = subjects.id
                     JOIN users teacher ON teacher.id = subject_teacher.teacher_id
                     WHERE teacher.id = ?;
                """;

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, teacherId);

            try (

                    ResultSet rs = stm.executeQuery();

            ) {
                while (rs.next()) {
                    SubjectToTeacher subject = new SubjectToTeacher();
                    subject.setGroupName(rs.getString("group_name"));
                    subject.setSubjectName(rs.getString("subject_name"));
                    subject.setSubjectId(rs.getLong("subject_id"));
                    subjects.add(subject);
                }
            }

        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("subjects", subjects);
    }

    @Override
    protected Map<String, Object> getSubjectById(Long subjectId) throws ExceptionHandler {
        final String SQL = """
                    SELECT
                        users.id AS student_id,
                        credentials.boleta AS username,
                        users.fullname AS student_name,
                        COALESCE(qualitications.qualification, 0) AS qualification
                    FROM users
                    JOIN subject_student ON subject_student.student_id = users.id
                    JOIN credentials ON credentials.user_id = users.id
                    LEFT JOIN qualitications ON qualitications.subject_id = subject_student.subject_id
                    WHERE subject_student.subject_id = ?;
                """;
        List<StudentsInSubject> students = new ArrayList<StudentsInSubject>();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, subjectId);

            try (
                    ResultSet rs = stm.executeQuery();) {

                while (rs.next()) {
                    StudentsInSubject student = new StudentsInSubject();
                    student.setQualification(rs.getLong("qualification"));
                    student.setStudentId(rs.getLong("student_id"));
                    student.setStudentFullName(rs.getString("student_name"));
                    student.setUsername(rs.getString("username"));
                    students.add(student);
                }

            }

        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
        return Map.of("students", students);
    }

    @Override
    protected Map<String, Object> updateQualification(UpdateQualificationDto dto) throws ExceptionHandler {
        final String SQL = "UPDATE qualitications SET qualification = ? WHERE id = ?";
        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, dto.getQualification());
            stm.setLong(2, dto.getId());
            stm.executeUpdate();

            return Map.of("message", "Qualification updated successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, Object> deleteQualification(Long qualificationId) throws ExceptionHandler {
        final String SQL = "DELETE FROM qualitications WHERE id = ?";
        
        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, qualificationId);
            stm.executeUpdate();

            return Map.of("message", "Qualification deleted successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }


    @Override
    protected Map<String, Object> registerQualification(RegisterQualificationDto dto) throws ExceptionHandler {
        final String SQL = "INSERT INTO qualitications (qualification, student_id, subject_id) VALUES (?, ?, ?)";

        Map<String, SubjectUser> userSubject = subjectUserService.getStudentToSubject(dto.getGetUserToSubject());
        if (userSubject.get("subject_user").getUserId() == null)
            throw new ExceptionHandler(ErrorsSubject.RECORD_STUDENT_NOT_EXIST, HttpStatus.HTTP_BAD_REQUEST);

        Map<String, Qualification> qualExit = getQualificationByStudenInSubject(dto.getGetUserToSubject().getSubjectId(), dto.getGetUserToSubject().getUserId());
        if(qualExit.get("qualification").getId() != null)
            throw new ExceptionHandler(ErrorsQualification.QUALIFICATION_ALREDY_EXIST, HttpStatus.HTTP_BAD_REQUEST);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, dto.getQualification());
            stm.setLong(2, dto.getGetUserToSubject().getUserId());
            stm.setLong(3, dto.getGetUserToSubject().getSubjectId());

            stm.executeUpdate();

            return Map.of("message", "Qualification registered successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    private Map<String, Qualification> getQualificationByStudenInSubject(Long subjectId, Long studentId) throws ExceptionHandler{
        final String SQL = "SELECT * FROM qualitications WHERE subject_id = ? AND student_id = ?";
        Qualification qualification = new Qualification();

        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, subjectId);
            stm.setLong(2, studentId);

            try(
                ResultSet rs = stm.executeQuery();
            ){
                if(rs.next()){
                    qualification.setId(rs.getLong("id"));
                }
            }
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("qualification", qualification);
    }


}
