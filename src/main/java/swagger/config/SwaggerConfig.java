package swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import proyectofinal.config.EnvsAdapter;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;

import java.util.Set;

public class SwaggerConfig {
    public static OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .addServersItem(new Server().url(EnvsAdapter.BACKEND_SERVER))
                .info(new Info()
                        .title("Safe school system")
                        .version("1.0")
                        .description(""));

        // Configurar Swagger para escanear los controladores
        SwaggerConfiguration config = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true)
                .resourcePackages(Set.of("swagger.api"));

        try {
            openAPI = new JaxrsOpenApiContextBuilder<>()
                    .openApiConfiguration(config)
                    .buildContext(true)
                    .read();
        } catch (OpenApiConfigurationException e) {
            e.printStackTrace();
        }

        return openAPI;
    }
}
