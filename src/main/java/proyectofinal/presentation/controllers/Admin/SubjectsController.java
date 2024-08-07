package proyectofinal.presentation.controllers.Admin;

import java.util.Map;
import spark.Request;
import spark.Response;

import proyectofinal.datasource.admin.subjects.SubjectServiceImpl;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.admin.subject.*;
import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.dtos.http.ParamIdDto;
import proyectofinal.domain.interfaces.Subject;
import proyectofinal.utils.GetBody;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.ValidateSintaxJson;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.directivo.ErrorsGroup;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;
import proyectofinal.utils.http.status.HttpStatus;


public class SubjectsController {

    private SubjectServiceImpl service;

    public SubjectsController(SubjectServiceImpl service) {
        this.service = service;
    }

    public final String getSubjects(Request req, Response res) {
        try {
            Map<String, Object> response = service.getSubjects();
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String getSubjectById(Request req, Response res) {
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));

        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()),
                    HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Subject> response = service.getSubjectById(paramIdDto.getDto().getParam());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String getSubjectByName(Request req, Response res) {
        if (req.queryParams("name") == null)
            return ResponseBody.jsonResponse(res, Map.of("eror", ErrorsGroup.PARAM_NAME_REQUIRED), HttpStatus.HTTP_BAD_REQUEST);

        String name = req.queryParams("name").trim();

        if (name.length() == 0)
            return getSubjects(req, res);

        try {
            Map<String, Subject> response = service.getSubjectByName(name);
            if(response.get("subject").getId() == null)
                return ResponseBody.jsonResponse(res, Map.of("eror", ErrorsSubject.SUBJECT_NOT_FOUND), HttpStatus.HTTP_NOT_FOUND);
                
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String registerSubject(Request req, Response res) {
        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<RegisterSubjectDto> resultDto = RegisterSubjectDto.validate(body.getBody());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, String> response = service.registerSubject(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String updateSubject(Request req, Response res){
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));
        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);
        
        ResultDto<UpdateSubjectDto> resultDto = UpdateSubjectDto.validate(body.getBody(), paramIdDto.getDto().getParam());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);
        
        try {
            Map<String, String> response = service.updateSubject(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String registerStudentToSubject(Request req, Response res) {
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));
        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<RegisterStudentDto> resultDto = RegisterStudentDto.validate(body.getBody(), paramIdDto.getDto().getParam());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, String> response = service.registerStudentToSubject(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }


    public final String deleteStudentToSubject(Request req, Response res) {
        String studentId = req.queryParams("studentId");
        String subjectId = req.queryParams("subjectId");
        
        ResultDto<GetUserToSubjectDto> resultDto = GetUserToSubjectDto.validateParams(studentId, subjectId);
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);
        
        try {
            Map<String, String> response = service.deleteStudentToSubject(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String deleteTeacherToSubject(Request req, Response res) {
        String teacherId = req.queryParams("teacherId");
        String subjectId = req.queryParams("subjectId");

        ResultDto<GetUserToSubjectDto> resultDto = GetUserToSubjectDto.validateParams(teacherId, subjectId);
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);
        
        try {
            Map<String, String> response = service.deleteTeacherToSubject(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String deleteSubject(Request req, Response res) {
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));

        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()),
                    HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, String> response = service.deleteSubject(paramIdDto.getDto().getParam());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
}
