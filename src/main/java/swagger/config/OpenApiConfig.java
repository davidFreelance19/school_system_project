package swagger.config;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

public class OpenApiConfig {
    private final OpenAPI openAPI;

    public OpenApiConfig(SwaggerConfiguration config) throws OpenApiConfigurationException {
        this.openAPI = new JaxrsOpenApiContextBuilder<>()
                .openApiConfiguration(config)
                .buildContext(true)
                .read();
    }

    public String getOpenApiJson() {
        return Json.pretty(openAPI);
    }
}
