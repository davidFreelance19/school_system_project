package swagger.api.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Sistem login authentication")
@Path("auth/")
public class AuthApi {

    @Path("/login")
    @POST
    @Produces({ "application/json" })
    @Consumes({ "application/json"})
    @Operation(
        summary = "Login a user",
        description = "Login a user with your credentials: name and password",
        operationId = "loginUser"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Login successfully",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                                "token": "... your token access ..."
                            }
                    """
                )              
            )
        ),
        @ApiResponse(responseCode = "404", description = "User not exit"),
        @ApiResponse(responseCode = "403", description = "Password incorrect"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Response login(
        @FormParam("boleta") String boleta,
        @FormParam("password") String password
    ){
        return Response.ok().build();
    }
}
