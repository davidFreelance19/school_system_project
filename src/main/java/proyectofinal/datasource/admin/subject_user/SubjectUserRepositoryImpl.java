package proyectofinal.datasource.admin.subject_user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.interfaces.SubjectUser;
import proyectofinal.domain.repositories.admin.SubjectUserRepository;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.status.HttpStatus;

public class SubjectUserRepositoryImpl extends SubjectUserRepository{

    @Override
    protected Map<String, SubjectUser> getStudentToSubject(GetUserToSubjectDto dto)throws ExceptionHandler {
        final String SQL = "SELECT * FROM subject_student WHERE subject_id = ? AND student_id = ?";
        SubjectUser subjectUser = new SubjectUser();
        
        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, dto.getSubjectId());
            stm.setLong(2, dto.getUserId());

            try(ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    subjectUser.setSubjectId(rs.getLong("subject_id"));
                    subjectUser.setUserId(rs.getLong("student_id"));
                }

            }
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("subject_user", subjectUser);
    }
}
