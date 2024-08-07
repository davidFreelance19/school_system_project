package proyectofinal.presentation.middlewares.auth;

import static spark.Spark.halt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.status.HttpStatus;
import proyectofinal.utils.security.Cripto;
import proyectofinal.utils.security.JWT;
import spark.Request;
import spark.Response;

public class AuthMiddleware {

    private static final Map<String, String> RES_INVALID_TOKEN = Map.of("error", ErrorsShared.INVALID_TOKEN);
    private static final Map<String, String> RES_INTERNAL_ERROR = Map.of("error", ErrorsShared.INTERNAL_SERVER_ERROR);
    private static final Map<String, String> RES_NOT_AUTHORIZATION = Map.of("error", ErrorsShared.ACCESS_UNAUTHORIZED);

    public static void auth(Request req, Response res) {
        String auth = req.headers("Authorization");
        if (auth == null)
            halt(HttpStatus.HTTP_NOT_AUTHORIZATION, ResponseBody.jsonResponse(res, RES_INVALID_TOKEN));

        if (!(auth.startsWith("Bearer ")))
            halt(HttpStatus.HTTP_NOT_AUTHORIZATION, ResponseBody.jsonResponse(res, RES_INVALID_TOKEN));

        if (!JWT.validateToken(auth.substring(auth.indexOf(" ") + 1, auth.length())))
            halt(HttpStatus.HTTP_NOT_AUTHORIZATION, ResponseBody.jsonResponse(res, RES_INVALID_TOKEN));

        Map<String, Object> payload = JWT.getPayload(auth.substring(auth.indexOf(" ") + 1, auth.length()));

        int id = (int) payload.get("id");
        User user = getUserById(res, id);

        if (user.getFullname() == null)
            halt(HttpStatus.HTTP_NOT_AUTHORIZATION, ResponseBody.jsonResponse(res, RES_INVALID_TOKEN));

        req.attribute("user", user);
    }

    public static void checkIsCorrectRole(Request req, Response res, String ROLE) {
        User user = (User) req.attribute("user");
        String role = "";

        try {
            role = Cripto.encrypt(ROLE);
        } catch (Exception e) {
            halt(HttpStatus.HTTP_INTERNAL_ERROR, ResponseBody.jsonResponse(res, RES_INTERNAL_ERROR));
        }

        if (!(role.equals(user.getRole())))
            halt(HttpStatus.HTTP_NOT_AUTHORIZATION, ResponseBody.jsonResponse(res, RES_NOT_AUTHORIZATION));

    }

    public static User getUserById(Response res, int id) {
        final String SQL = "SELECT * FROM users WHERE id = ?";
        User user = new User();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {

            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery()) {

                if (result.next()) {
                    user.setId(result.getLong("id"));
                    user.setFullname(result.getString("fullname"));
                    user.setRole(result.getString("role"));
                }

            }

        } catch (SQLException e) {
            halt(HttpStatus.HTTP_INTERNAL_ERROR, ResponseBody.jsonResponse(res, RES_INTERNAL_ERROR));
        }

        return user;
    }
}
