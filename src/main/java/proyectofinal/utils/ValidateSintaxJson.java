package proyectofinal.utils;

import java.lang.reflect.Type;
import java.util.Map;

import spark.Response;
import spark.Request;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import proyectofinal.utils.http.errors.ErrorsShared;

public class ValidateSintaxJson {

    private static Gson gson = new Gson();

    public static GetBody validateSyntax(Request req, Response res) {
        if (req.body().isEmpty())
            return GetBody.ofMessage(ErrorsShared.BODY_EMPTY_ERROR);

        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        try {
            Map<String, Object> body = gson.fromJson(req.body(), type);
            return GetBody.ofBody(body);
        } catch (JsonSyntaxException e) {
            return GetBody.ofMessage(ErrorsShared.JSON_SYNTAX_ERROR);
        }
    }

}
