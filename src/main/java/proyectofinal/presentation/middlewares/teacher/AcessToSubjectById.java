package proyectofinal.presentation.middlewares.teacher;

import static spark.Spark.halt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import spark.Request;
import spark.Response;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.http.ParamIdDto;
import proyectofinal.domain.interfaces.Subject;
import proyectofinal.domain.interfaces.SubjectUser;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;
import proyectofinal.utils.http.status.HttpStatus;


public class AcessToSubjectById {
    
    public static void validateAccessBySubjectId(Request req, Response res){
        User user = (User) req.attribute("user");
        ResultDto<ParamIdDto> paramDto = ParamIdDto.getParam(req.params("subjectId"));
        if(paramDto.getMessage() != null)
            halt(HttpStatus.HTTP_BAD_REQUEST, ResponseBody.jsonResponse(res, Map.of("error", paramDto.getMessage())));
        
        SubjectUser subjectUser = getSubjectUserBySubjectId(paramDto.getDto().getParam(), res);
        if(subjectUser.getUserId() != user.getId())
            halt(HttpStatus.HTTP_FORBIDDEN, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.ACCESS_DENIED)));
        
        req.attribute("param", paramDto.getDto().getParam());
    }

    private static Subject getSubjectById(Long subjectId, Response res){
        Subject subject = new Subject();
        final String SQL = "SELECT * FROM subjects WHERE id = ?";
        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, subjectId);
            
            try(
                ResultSet rs = stm.executeQuery();
            ){
                if(rs.next()){
                    subject.setId(rs.getLong("id"));
                }

            }

        } catch (SQLException e) {
            halt(HttpStatus.HTTP_INTERNAL_ERROR, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.INTERNAL_SERVER_ERROR)));
        }
        return subject;
    }

    private static SubjectUser getSubjectUserBySubjectId(Long subjectId, Response res){
        Subject subject = getSubjectById(subjectId, res);
        if(subject.getId() == null)
            halt(HttpStatus.HTTP_NOT_FOUND, ResponseBody.jsonResponse(res, Map.of("error", ErrorsSubject.SUBJECT_NOT_FOUND)));

        SubjectUser subjectUser = new SubjectUser();
        final String SQL = "SELECT * FROM subject_teacher WHERE subject_id = ?";
    
        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, subjectId);
            
            try(
                ResultSet rs = stm.executeQuery();
            ){
                if(rs.next()){
                    subjectUser.setSubjectId(rs.getLong("subject_id"));
                    subjectUser.setUserId(rs.getLong("teacher_id"));
                }
            }

        } catch (SQLException e) {
            halt(HttpStatus.HTTP_INTERNAL_ERROR, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.INTERNAL_SERVER_ERROR)));
        }

        if(subjectUser.getSubjectId() == null)    
            halt(HttpStatus.HTTP_FORBIDDEN, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.ACCESS_DENIED)));
        
        return subjectUser;
    }
}
