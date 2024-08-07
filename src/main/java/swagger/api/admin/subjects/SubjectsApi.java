package swagger.api.admin.subjects;

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
import swagger.models.SubjectSchema;
import swagger.models.UserSchema;

@Tag(name = "Admin - Subjects", description = "Routes for the administration of subjects for the school system")
@Path("/admin/subjects")
public class SubjectsApi {

    @Path("//")
    @GET
    @Produces({"application/json"})
    @Operation(
        summary = "Get the subjects information", 
        operationId = "getSubjects", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SubjectSchema.class),
                examples = @ExampleObject(
                    value = """
                                {
                                    "subjects": [
                                        {
                                            "subject": {
                                                "id": 1,
                                                "name": "Subject 1",
                                                "groupdId": 10,
                                                "teacher": {
                                                    "id": 1,
                                                    "fullname": "Teacher 1"
                                                }
                                            }
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
    public Response getSubjects(){
        return Response.ok().build();
    }

    @GET
    @Path("/get-subject/{id}")
    @Produces({ "application/json"})
    @Operation(
        summary = "Get information a subject by id", 
        operationId = "getSubjectById", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubjectSchema.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                    "subject": {
                                        "id": 1,
                                        "name": "Subject by ID 1",
                                        "groupId": 10,
                                        "teacher": {
                                            "id": 1,
                                            "fullname": "Teacher 1"
                                        },
                                        "students": [
                                            {
                                                "id": 1,
                                                "fullname": "Student 1"
                                            },
                                            {
                                                "id": 2,
                                                "fullname": "Student 2"
                                            }
                                        ]
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Subject not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getSubjectById(
        @Parameter(description = "Id of the subject to retrieve", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @GET
    @Path("/get-subject")
    @Produces({ "application/json"})
    @Operation(
        summary = "Get information a subject by name", 
        operationId = "getSubjectByName", 
        security = @SecurityRequirement(name = "Auth Admin")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubjectSchema.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                    "subject": {
                                        "id": 1,
                                        "name": "Subject by ID 1",
                                        "groupId": 10
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Subject not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getSubjectByName(
        @Parameter(description = "Name of the subject to retrieve")
        @QueryParam("name") String name
    ){
        return Response.ok().build();
    }

    @POST
    @Path("//")
    @Produces({ "application/json"})
    @Consumes({ "application/json" })
    @Operation(
        summary = "Register a subject.",
        operationId = "registerSubject", 
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
                                   "message": "Subject created successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response registerSubject(
        @FormParam(value = "name") String name,
        @FormParam(value = "groupName") String groupName,
        @FormParam(value = "teacherFullName") String teacherFullName
    ){
        return Response.ok().build();
    }


    @POST
    @Path("/register-student/{id}")
    @Produces({ "application/json"})
    @Consumes({ "application/json" })
    @Operation(
        summary = "Register a student to the subject using subjectId and the student fullname.",
        operationId = "registerStudentToSubject", 
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
                                   "message": "Student register to the subject successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "User or subject not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response registerStudentSubject(
        @FormParam(value = "fullname") String fullname,
        @Parameter(description = "Subject ID to register student", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @Produces({ "application/json" })
    @Consumes({ "application/json" })
    @Operation(
        summary = "Update properties a subject for example: group, teacher or subject name.", 
        operationId = "updateSubject", 
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
                                   "message": "Subject updated successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Subject not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response updateTeacher(
        @FormParam(value = "name") String name,
        @FormParam(value = "groupName") String groupName,
        @FormParam(value = "teacherFullName") String teacherFullName,
        @Parameter(description = "ID of the subject to update", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @DELETE
    @Path("remove-student")
    @Operation(
        summary = "Remove a student from the subject with the provided subject ID and teacher ID",
        operationId = "deleteStudentToSubject",
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
                                "message": "The student has been successfully removed from the subject"
                            }
                        """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Access unauthorized"),
        @ApiResponse(responseCode = "404", description = "User or subject not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deleteStudentToSubject(
        @Parameter(description = "The subjectId of the subject")
        @QueryParam("subjectId") String subjectId,
        @Parameter(description = "The studentId of the student")
        @QueryParam("studentId") String studenttId
    ){
        return Response.ok().build();
    }

    @DELETE
    @Path("remove-teacher")
    @Operation(
        summary = "Remove a teacher from the subject with the provided subject ID and teacher ID",
        operationId = "deleteTeacherToSubject",
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
                                "message": "The teacher has been successfully removed from the subject"
                            }
                        """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Access unauthorized"),
        @ApiResponse(responseCode = "404", description = "Subject not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deleteTeacherToSubject(
        @Parameter(description = "The subjectId of the subject")
        @QueryParam("subjectId") String subjectId,
        @Parameter(description = "The teacherId of the teacher")
        @QueryParam("teacherId") String teacherId
    ){
        return Response.ok().build();
    }
    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete a subject with the given id",
        operationId = "deleteSubject",
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
                                "message": "Subject deleted successfully"
                            }
                        """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Access unauthorized"),
        @ApiResponse(responseCode = "404", description = "Subject not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deleteSubject(
        @Parameter(description = "Subject id to delete", required = true)
        @PathParam(value = "id") String id
    ){
        return Response.ok().build();
    }
}
