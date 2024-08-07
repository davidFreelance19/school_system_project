package proyectofinal.presentation.router.admin;

import static spark.Spark.*;

import proyectofinal.config.EnvsAdapter;
import proyectofinal.presentation.middlewares.auth.AuthMiddleware;
import spark.Request;
import spark.Response;
import static spark.Spark.path;

public class AdminRoutes {

    public static void routes() {

        before("/*", AuthMiddleware::auth);
        before(
            "/*",
            (Request req, Response res) -> AuthMiddleware.checkIsCorrectRole(req, res, EnvsAdapter.ROLE_ADMIN)
        );

        path("/users", () -> UsersRoutes.routes());
        path("/groups", () -> GroupsRoutes.routes());
        path("/subjects", () -> SubjectsRoutes.routes());
    }
}
