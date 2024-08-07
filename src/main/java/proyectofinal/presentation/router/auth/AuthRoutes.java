package proyectofinal.presentation.router.auth;

import static spark.Spark.*;

import proyectofinal.datasource.auth.AuthRepositoryImpl;
import proyectofinal.datasource.auth.AuthServiceImpl;
import proyectofinal.presentation.controllers.auth.AuthController;

public class AuthRoutes {
    public static void routes() {
        AuthRepositoryImpl authRepository = new AuthRepositoryImpl();
        AuthServiceImpl authService = new AuthServiceImpl(authRepository);
        AuthController controller = new AuthController(authService);

        post("/login", controller::login);
    }
}
