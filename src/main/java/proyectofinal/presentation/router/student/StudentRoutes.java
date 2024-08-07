package proyectofinal.presentation.router.student;

import static spark.Spark.*;

import proyectofinal.config.EnvsAdapter;
import proyectofinal.datasource.student.StudentRepositoryImpl;
import proyectofinal.datasource.student.StudentServiceImpl;
import proyectofinal.presentation.controllers.student.StudentController;
import proyectofinal.presentation.middlewares.auth.AuthMiddleware;
import spark.Request;
import spark.Response;

public class StudentRoutes {

    public static void routes() {
        StudentRepositoryImpl repository = new StudentRepositoryImpl();
        StudentServiceImpl service = new StudentServiceImpl(repository);
        StudentController controller = new StudentController(service);

        before("/*", AuthMiddleware::auth);
        before("/*", (Request req, Response res) -> AuthMiddleware.checkIsCorrectRole(req, res, EnvsAdapter.ROLE_STUDENT));

        get("/", controller::getStudent);
    }

}
