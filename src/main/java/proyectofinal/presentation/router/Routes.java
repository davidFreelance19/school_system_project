package proyectofinal.presentation.router;

import static spark.Spark.path;

import proyectofinal.presentation.router.admin.AdminRoutes;
import proyectofinal.presentation.router.auth.AuthRoutes;
import proyectofinal.presentation.router.student.StudentRoutes;
import proyectofinal.presentation.router.teacher.TeacherRoutes;

public class Routes {

    public static void getRoutes() {
        path("/auth", () -> AuthRoutes.routes());
        path("/admin", () -> AdminRoutes.routes());
        path("/student", () -> StudentRoutes.routes());
        path("/teacher", () -> TeacherRoutes.routes());
    }

}
