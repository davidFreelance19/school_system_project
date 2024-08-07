package proyectofinal.presentation.controllers.teacher;

import java.util.Map;

import proyectofinal.datasource.teacher.TeacherServiceImpl;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.teacher.RegisterQualificationDto;
import proyectofinal.domain.dtos.teacher.UpdateQualificationDto;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.GetBody;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.ValidateSintaxJson;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.status.HttpStatus;
import spark.Request;
import spark.Response;

public class TeacherController {

    private TeacherServiceImpl service;

    public TeacherController(TeacherServiceImpl service) {
        this.service = service;
    }

    public String getSubjectsToTeacher(Request req, Response res) {
        User user = (User) req.attribute("user");
        
        try {
            Map<String, Object> response = service.getSubjectsToTeacher(user.getId());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
    
    public String getSubjectById(Request req, Response res) {
        Long subjectId = (Long) req.attribute("param");
        
        try {
            Map<String, Object> response = service.getSubjectById(subjectId);
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
    
    public String registerQualification(Request req, Response res) {
        Long subjectId = (Long) req.attribute("param");

        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<RegisterQualificationDto> resultDto = RegisterQualificationDto.validate(body.getBody(), subjectId);
        if(resultDto.getMessage() != null) 
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Object> response = service.registerQualification(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String updateQualification(Request req, Response res) {
        Long qualificationId = (Long) req.attribute("param");

        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<UpdateQualificationDto> resultDto = UpdateQualificationDto.update(body.getBody(), qualificationId);
        if(resultDto.getMessage() != null) 
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Object> response = service.updateQualification(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String deleteQualification(Request req, Response res) {
        Long qualificationId = (Long) req.attribute("param");

        try {
            Map<String, Object> response = service.deleteQualification(qualificationId);
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
}
