package proyectofinal.datasource.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.interfaces.student.QualificationsStudent;
import proyectofinal.domain.repositories.student.StudentRepository;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.status.HttpStatus;

public class StudentRepositoryImpl extends StudentRepository {

    @Override
    public Map<String, Object> getQualificationsStudent(Long studentId) throws ExceptionHandler {
        List<QualificationsStudent> qualifications = new ArrayList<QualificationsStudent>();

        final String SQL = """
                    SELECT
                        subjects.id AS subject_id,
                        groups.name AS group_name,
                        subjects.name AS subject_name,
                        COALESCE(teacher.fullname, '-') AS teacher_name,
                        COALESCE(qualitications.qualification, 0) AS qualification
                    FROM subjects
                    JOIN groups ON groups.id = subjects.group_id
                    LEFT JOIN qualitications ON qualitications.subject_id = subjects.id
                    LEFT JOIN subject_teacher ON subject_teacher.subject_id = subjects.id
                    LEFT JOIN users teacher ON teacher.id = subject_teacher.teacher_id
                    JOIN subject_student ON subject_student.subject_id = subjects.id
                    JOIN users student ON student.id = subject_student.student_id
                    WHERE student.id = ?
                """;
        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, studentId);

            try (
                    
                ResultSet rs = stm.executeQuery();
                    
            ) {

                while (rs.next()) {
                    QualificationsStudent qualification = new QualificationsStudent();
                    qualification.setGroupName(rs.getString("group_name"));
                    qualification.setQualification(rs.getLong("qualification"));
                    qualification.setSubjectId(rs.getLong("subject_id"));
                    qualification.setSubjectName(rs.getString("subject_name"));
                    qualification.setTeacher(rs.getString("teacher_name"));
                    qualifications.add(qualification);
                }

            }

        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("qualifications", qualifications);
    }

}
