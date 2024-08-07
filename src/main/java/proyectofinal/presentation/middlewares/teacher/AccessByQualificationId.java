package proyectofinal.presentation.middlewares.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import spark.Request;
import spark.Response;
import static spark.Spark.halt;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.http.ParamIdDto;
import proyectofinal.domain.interfaces.Qualification;
import proyectofinal.domain.interfaces.SubjectUser;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.teacher.ErrorsQualification;
import proyectofinal.utils.http.status.HttpStatus;

public class AccessByQualificationId {
    
    public static void validateAccess(Request req, Response res){
        User user = (User) req.attribute("user");
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));
        if (paramIdDto.getDto() == null)
            halt(HttpStatus.HTTP_BAD_REQUEST, ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage())));
        
        SubjectUser subjectUser = getSubjectUserBySubjectId(paramIdDto.getDto().getParam(), res);
        if(subjectUser.getUserId() != user.getId())
            halt(HttpStatus.HTTP_FORBIDDEN, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.ACCESS_DENIED)));
        
        req.attribute("param", paramIdDto.getDto().getParam());
    }

    private static Qualification getQualificationById(Long id, Response res){
        final String SQL = "SELECT * FROM qualitications WHERE id = ?";
        Qualification qualification = new Qualification();
        
        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, id);
            try(
                ResultSet rs = stm.executeQuery();
            ){
                if(rs.next()){
                    qualification.setSubjectId(rs.getLong("subject_id"));
                }
            }

        } catch (Exception e) {
            halt(HttpStatus.HTTP_INTERNAL_ERROR, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.INTERNAL_SERVER_ERROR)));
        }

        return qualification;
    }

    private static SubjectUser getSubjectUserBySubjectId(Long qualificationId, Response res){
        final String SQL = "SELECT * FROM subject_teacher WHERE subject_id = ?";
        
        Qualification qualification = getQualificationById(qualificationId, res);
        if(qualification.getSubjectId() == null)
            halt(HttpStatus.HTTP_BAD_REQUEST, ResponseBody.jsonResponse(res, Map.of("error", ErrorsQualification.QUALIFICATION_NOT_EXIST)));
        
        SubjectUser subjectUser = new SubjectUser();

        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ){
            stm.setLong(1, qualification.getSubjectId());

            try(
                ResultSet rs = stm.executeQuery();
            ){
                if(rs.next()){
                    subjectUser.setUserId(rs.getLong("teacher_id"));
                }
            }

        } catch (Exception e) {
            halt(HttpStatus.HTTP_INTERNAL_ERROR, ResponseBody.jsonResponse(res, Map.of("error", ErrorsShared.INTERNAL_SERVER_ERROR)));
        }
        return subjectUser;
    }

}
