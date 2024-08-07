package proyectofinal.presentation.middlewares.admin;

import proyectofinal.config.EnvsAdapter;
import spark.Request;
import spark.Response;

public class Role {

    public static void asignationAdmin(Request req, Response res) {
        req.attribute("role", EnvsAdapter.ROLE_ADMIN);
    }

    public static void asignationTeacher(Request req, Response res) {
        req.attribute("role", EnvsAdapter.ROLE_TEACHER);
    }

    public static void asignationStudent(Request req, Response res) {
        req.attribute("role", EnvsAdapter.ROLE_STUDENT);
    }

}
