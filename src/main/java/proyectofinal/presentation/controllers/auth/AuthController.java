package proyectofinal.presentation.controllers.auth;

import java.util.Map;

import proyectofinal.datasource.auth.AuthServiceImpl;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.auth.LoginDto;
import proyectofinal.utils.GetBody;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.ValidateSintaxJson;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.status.HttpStatus;
import spark.Request;
import spark.Response;

public class AuthController {

    private AuthServiceImpl service;

    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    public String login(Request req, Response res) {
        GetBody body = ValidateSintaxJson.validateSyntax(req, res);

        if (!body.isValid())
            return ResponseBody.jsonResponse(res, Map.of("error", body.getErrorMessage()), HttpStatus.HTTP_BAD_REQUEST);

        ResultDto<LoginDto> loginDto = LoginDto.validate(body.getBody());
        if (loginDto.getMessage() != null)
            return ResponseBody.jsonResponse(res, Map.of("error", loginDto.getMessage()), HttpStatus.HTTP_BAD_REQUEST);

        try {
            Map<String, Object> response = service.login(loginDto.getDto());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }

    }
}
