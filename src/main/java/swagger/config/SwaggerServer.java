package swagger.config;

import static spark.Spark.*;
import java.util.Set;
import io.swagger.v3.oas.integration.SwaggerConfiguration;

public class SwaggerServer {

    public static void start() {
        
        SwaggerConfiguration config = new SwaggerConfiguration()
                .openAPI(SwaggerConfig.customOpenAPI())
                .prettyPrint(true)
                .resourcePackages(Set.of("presentation.controllers"));

        get("/swagger-ui", (req, res) -> {
            res.redirect("/swagger-ui/index.html");
            return null;
        });

        get("/swagger", (req, res) -> {
            OpenApiConfig openApiHandler = new OpenApiConfig(config);
            return openApiHandler.getOpenApiJson();
        });

    }
}
