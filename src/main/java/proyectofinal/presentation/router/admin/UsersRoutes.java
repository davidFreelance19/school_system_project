package proyectofinal.presentation.router.admin;

import static spark.Spark.*;

import proyectofinal.datasource.admin.users.UserRepositoryImpl;
import proyectofinal.datasource.admin.users.UserServiceImpl;
import proyectofinal.presentation.controllers.Admin.UsersController;
import proyectofinal.presentation.middlewares.admin.Role;

public class UsersRoutes {
    public static void routes() {
        UserRepositoryImpl repository = new UserRepositoryImpl();
        UserServiceImpl service = new UserServiceImpl(repository);
        UsersController controller = new UsersController(service);

        before("/register-admin", Role::asignationAdmin);
        post("/register-admin", controller::registerUserByRole);

        before("/students/*", Role::asignationStudent);
        post("/students/", controller::registerUserByRole);
        get("/students/", controller::getUserByRole);
        get("/students/get-student", controller::getUserByFullNameAndRole);
        get("/students/:id", controller::getUserByIdAndRole);
        put("/students/:id", controller::updateUser);
        delete("/students/:id", controller::deleteUser);

        before("/teachers/*", Role::asignationTeacher);
        post("/teachers/", controller::registerUserByRole);
        get("/teachers/", controller::getUserByRole);
        get("/teachers/get-teacher", controller::getUserByFullNameAndRole);
        get("/teachers/:id", controller::getUserByIdAndRole);
        put("/teachers/:id", controller::updateUser);
        delete("/teachers/:id", controller::deleteUser);
    }
}
