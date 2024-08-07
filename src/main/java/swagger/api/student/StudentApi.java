package swagger.api.student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student", description = "Student endpoint")
@Path("/student")
@SecurityScheme(
    name = "Auth Student",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class StudentApi {

    @GET
    @Path("//")
    @Produces({"application/json"})
    @Operation(summary = "Obteniendo las calificaciones del estudiante", operationId = "getCalificaciones", security = @SecurityRequirement(name = "Auth Student"))
    @ApiResponses(
        @ApiResponse(responseCode = "500", description = "Internal server error")
    )    
    public Response getCalificaciones(){
        return Response.ok().build();
    }
}
