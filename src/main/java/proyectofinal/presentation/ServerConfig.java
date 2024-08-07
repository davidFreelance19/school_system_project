package proyectofinal.presentation;

import static spark.Spark.*;

import proyectofinal.config.EnvsAdapter;
import proyectofinal.presentation.router.Routes;

public class ServerConfig {

    public static final void start() {
        staticFiles.location("/public");
        port(EnvsAdapter.PORT);
        Routes.getRoutes();
    }
}
