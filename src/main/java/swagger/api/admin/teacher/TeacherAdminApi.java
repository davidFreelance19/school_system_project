package swagger.api.admin.teacher;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import swagger.models.GroupSchema;
import swagger.models.UserSchema;

@Tag(name = "Admin - Teachers", description = "CRUD of teachers for the school system")
@Path("/admin/users/teachers")
public class TeacherAdminApi {

    @Path("//")
    @GET
    @Produces({"application/json"})
    @Operation(
        summary = "Get teachers information", 
        operationId = "getTeachersInAdminRoute", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserSchema.class),
                examples = @ExampleObject(
                    value = """
                                {
                                    "teachers": [
                                        {
                                            "id": 1,
                                            "name": "Teacher 1"
                                        },
                                        {
                                            "id": 2,
                                            "name": "Teacher 2"
                                        }
                                    ]
                                }
                            """
                )
            )    
        ),
        @ApiResponse(responseCode = "401", description = "Access unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public Response  getTeacher(){
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces({ "application/json"})
    @Operation(
        summary = "Get information a teacher by id", 
        operationId = "getTeacherInAdminRoot", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserSchema.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                    "teacher": {
                                        "id": 1,
                                        "fullname": "Teacher by ID 1"
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Teacher not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getTeacherById(
        @Parameter(description = "Id of the teacher to retrieve", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @GET
    @Path("/get-teacher")
    @Produces({ "application/json"})
    @Operation(
        summary = "Get information a teacher by name", 
        operationId = "getTeacherByNameInAdminRoot", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserSchema.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                    "teacher": {
                                        "id": 1,
                                        "name": "Teacher by ID 1"
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Teacher not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getTeacherByName(
        @Parameter(description = "Fullname of the teacher to retrieve", required = true)
        @QueryParam("fullname") String fullname
    ){
        return Response.ok().build();
    }

    @POST
    @Path("//")
    @Produces({ "application/json"})
    @Consumes({ "application/json" })
    @Operation(
        summary = "Register a teacher.",
        operationId = "registerTeacher", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserSchema.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                   "message": "User created successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response registerTeacher(
        @FormParam(value = "fullname") String fullname,
        @FormParam(value = "email") String email
    ){
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @Produces({ "application/json" })
    @Consumes({ "application/json" })
    @Operation(
        summary = "Update properties a teacher for example: email, username or both.", 
        operationId = "updateTeacher", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GroupSchema.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                   "message": "Teacher updated successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Teacher not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response updateTeacher(
        @FormParam(value = "fullname") String fullname,
        @FormParam(value = "email") String email,
        @Parameter(description = "ID of the teacher to update", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete a teacher with the given id",
        operationId = "deleteTeacher",
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Operation successfull",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                                "message": "Teacher deleted successfully",
                            }
                        """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Access unauthorized"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deleteTeacher(
        @Parameter(description = "Teacher id to delete", required = true)
        @PathParam(value = "id") String id
    ){
        return Response.ok().build();
    }
}
