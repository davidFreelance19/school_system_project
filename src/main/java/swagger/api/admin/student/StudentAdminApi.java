package swagger.api.admin.student;

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

@Tag(name = "Admin - Students", description = "CRUD of students for the school system")
@Path("/admin/users/students")
public class StudentAdminApi {

    @Path("//")
    @GET
    @Produces({"application/json"})
    @Operation(
        summary = "Get students information", 
        operationId = "getStudentsInAdminRoute", 
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
                                    "students": [
                                        {
                                            "id": 1,
                                            "name": "Student 1"
                                        },
                                        {
                                            "id": 2,
                                            "name": "Student 2"
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
    public Response  getStudents(){
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces({ "application/json"})
    @Operation(
        summary = "Get information a student by id", 
        operationId = "getStudentInAdminRoot", 
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
                                    "student": {
                                        "id": 1,
                                        "fullname": "Student by ID 1"
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Student not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getStudentById(
        @Parameter(description = "Id of the student to retrieve", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @GET
    @Path("/get-student")
    @Produces({ "application/json"})
    @Operation(
        summary = "Get information a student by name", 
        operationId = "getStudentByNameInAdminRoot", 
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
                                    "student": {
                                        "id": 1,
                                        "name": "Student by ID 1"
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Student not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getStudentByName(
        @Parameter(description = "Fullname of the student to retrieve", required = true)
        @QueryParam("fullname") String fullname
    ){
        return Response.ok().build();
    }

    @POST
    @Path("//")
    @Produces({ "application/json"})
    @Consumes({ "application/json" })
    @Operation(
        summary = "Register a student.",
        operationId = "registerStudent", 
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
    public Response registerStudent(
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
        summary = "Update properties a student for example: email, username or both.", 
        operationId = "updateStudent", 
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
                                   "message": "Student updated successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Student not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response updateStudent(
        @FormParam(value = "fullname") String fullname,
        @FormParam(value = "email") String email,
        @Parameter(description = "ID of the student to update", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete a student with the given id",
        operationId = "deleteStudent",
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
                                "message": "Student deleted successfully",
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
    public Response deleteStudent(
        @Parameter(description = "Student id to delete", required = true)
        @PathParam(value = "id") String id
    ){
        return Response.ok().build();
    }
}
