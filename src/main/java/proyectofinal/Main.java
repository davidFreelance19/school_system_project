package proyectofinal;

import proyectofinal.presentation.ServerConfig;
import swagger.config.SwaggerServer;

public class Main {
    public static void main(String[] args) {
        ServerConfig.start();
        SwaggerServer.start();
    }
}