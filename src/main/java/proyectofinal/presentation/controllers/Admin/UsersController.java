package proyectofinal.presentation.controllers.Admin;

import java.util.Map;

import spark.Request;
import spark.Response;
import proyectofinal.datasource.admin.users.UserServiceImpl;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.admin.user.CreateUserDto;
import proyectofinal.domain.dtos.admin.user.UpdateUserDto;
import proyectofinal.domain.dtos.http.ParamIdDto;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.GetBody;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.ValidateSintaxJson;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.directivo.ErrorsUser;
import proyectofinal.utils.http.status.HttpStatus;

public class UsersController {

    private UserServiceImpl directivoService;

    public UsersController(UserServiceImpl directivoService) {
        this.directivoService = directivoService;
    }

    public final String registerUserByRole(Request req, Response res) {
        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<CreateUserDto> resultDto = CreateUserDto.create(body.getBody());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            String role = (String) req.attribute("role");
            Map<String, Object> response = directivoService.registerUser(resultDto.getDto(), role);
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String getUserByRole(Request req, Response res) {
        try {
            String role = (String) req.attribute("role");
            Map<String, Object> response = directivoService.getUsersByRole(role);
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String getUserByIdAndRole(Request req, Response res) {
        ResultDto<ParamIdDto> paramId = ParamIdDto.getParam(req.params("id"));

        if (paramId.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramId.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            String role = (String) req.attribute("role");
            Map<String, User> response = directivoService.getUserByIdAndRole(paramId.getDto().getParam(), role);

            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String getUserByFullNameAndRole(Request req, Response res) {
        if (req.queryParams("fullname") == null)
            return ResponseBody.jsonResponse(res, Map.of("eror", ErrorsUser.PARAM_FULLNAME_REQUIRED),
                    HttpStatus.HTTP_BAD_REQUEST);

        String fullname = req.queryParams("fullname").trim();
        if (fullname.length() == 0)
            return getUserByRole(req, res);

        try {
            String role = (String) req.attribute("role");
            Map<String, User> response = directivoService.getUserByFullNameAndRole(fullname, role);

            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String updateUser(Request req, Response res) {
        ResultDto<ParamIdDto> paramId = ParamIdDto.getParam(req.params("id"));
        if (paramId.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramId.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        GetBody body = ValidateSintaxJson.validateSyntax(req, res);
        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<UpdateUserDto> resultDto = UpdateUserDto.update(body.getBody(), paramId.getDto().getParam());
        if (resultDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", resultDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            String role = (String) req.attribute("role");
            Map<String, Object> response = directivoService.updateUser(resultDto.getDto(), role);

            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String deleteUser(Request req, Response res) {
        ResultDto<ParamIdDto> paramId = ParamIdDto.getParam(req.params("id"));
        if (paramId.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", paramId.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            String role = (String) req.attribute("role");
            Map<String, Object> response = directivoService.deleteUser(paramId.getDto().getParam(), role);

            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
}
