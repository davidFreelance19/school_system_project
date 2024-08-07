package swagger.api.admin;

import javax.ws.rs.Path;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityScheme(
    name = "Auth Admin",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
@Tag(name="Admin", description = "The admin PATH")
@Path("/admin")
public class AdminApi {
    
}
