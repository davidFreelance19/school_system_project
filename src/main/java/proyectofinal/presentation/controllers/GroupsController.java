package proyectofinal.presentation.controllers;

import java.util.Map;

import proyectofinal.datasource.admin.groups.GroupsServiceImpl;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.admin.groups.RegisterGroupDto;
import proyectofinal.domain.dtos.admin.groups.UpdateGroupDto;
import proyectofinal.domain.dtos.http.ParamIdDto;
import proyectofinal.domain.interfaces.Group;
import proyectofinal.utils.GetBody;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.ValidateSintaxJson;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.directivo.ErrorsGroup;
import proyectofinal.utils.http.status.HttpStatus;
import spark.Request;
import spark.Response;

public class GroupsController {

    private GroupsServiceImpl service;

    public GroupsController(GroupsServiceImpl service) {
        this.service = service;
    }

    public final String getGroups(Request req, Response res) {
        try {
            Map<String, Object> response = service.getGroups();
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String getGroupById(Request req, Response res) {
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));

        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()),
                    HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Group> response = service.getGroupById(paramIdDto.getDto().getParam());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String getGroupByName(Request req, Response res) {
        if (req.queryParams("name") == null)
            return ResponseBody.jsonResponse(res, Map.of("eror", ErrorsGroup.PARAM_NAME_REQUIRED),
                    HttpStatus.HTTP_BAD_REQUEST);

        String name = req.queryParams("name").trim();

        if (name.length() == 0)
            return getGroups(req, res);

        try {
            Map<String, Group> response = service.getGroupByName(name);
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String registerGroup(Request req, Response res) {
        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<RegisterGroupDto> resultDto = RegisterGroupDto.validate(body.getBody());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Object> response = service.registerGroup(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String updateGroup(Request req, Response res) {
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));
        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()),
                    HttpStatus.HTTP_BAD_REQUEST);

        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<UpdateGroupDto> resultDto = UpdateGroupDto.update(body.getBody(), paramIdDto.getDto().getParam());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Object> response = service.updatedGroup(resultDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public final String deleteGroup(Request req, Response res) {
        ResultDto<ParamIdDto> paramIdDto = ParamIdDto.getParam(req.params("id"));

        if (paramIdDto.getDto() == null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramIdDto.getMessage()),
                    HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Object> response = service.deleteGroup(paramIdDto.getDto().getParam());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
}
