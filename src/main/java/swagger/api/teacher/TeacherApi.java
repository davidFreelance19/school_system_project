package swagger.api.teacher;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Teacher", description = "Obtener todos las asignaturas que imparte el profesor, CRUD de calificacion a un alumno")
@Path("/teacher/subjects")
@SecurityScheme(
    name = "Auth Teacher",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class TeacherApi{

    @GET
    @Path("/")
    @Produces({"application/json"})
    @Operation(
        summary = "Obtener todos las asignaturas que imparte el profesor", 
        operationId = "getSubjectToTeacher",
         security = @SecurityRequirement(name = "Auth Teacher")
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    public Response getSubjectById(){
        return Response.ok().build();
    }

    @GET
    @Path("/{subjectId}")
    @Produces({"application/json"})
    @Operation(
        summary = "Obtener una asignatura por id", 
        operationId = "getSubjectByIdInTeacherRoot",
        security = @SecurityRequirement(name = "Auth Teacher")
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access forbidden"),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    public Response getSubjectsToTeacher(
        @Parameter(description ="Subject ID to retrieve", required = true)
        @PathParam("subjectId") String subjectId
    ){
        return Response.ok().build();
    }

    @POST
    @Path("/register-qualification/{subjectId}")
    @Produces({"application/json"})
    @Operation(
        summary = "Registrar la calificacion de un alumno", 
        operationId = "registerQualification",
        security = @SecurityRequirement(name = "Auth Teacher")
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access forbidden"),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    public Response registerQualification(
        @Parameter(description ="Subject ID to retrieve", required = true)
        @PathParam("subjectId") String subjectId,
        @FormParam(value = "qualification") Long qualification,
        @FormParam(value = "studentId") Long studentId
    ){
        return Response.ok().build();
    }

    @PATCH
    @Path("/update-qualification/{id}")
    @Produces({ "application/json" })
    @Consumes({ "application/json"})
    @Operation(
        summary = "Actualizar la calificacion de un alumno", 
        operationId = "updateQualification",
        security = @SecurityRequirement(name = "Auth Teacher")
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access forbidden"),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    public Response updateQualification(
        @Parameter(description ="Qualification ID to update", required = true)
        @PathParam("id") String id,
        @FormParam(value = "qualification") String qualification
    ){
        return Response.ok().build();
    }


    @DELETE
    @Path("/delete-qualification/{id}")
    @Produces({"application/json"})
    @Operation(
        summary = "Dar de baja la calificacion de un alumno", 
        operationId = "deleteQualification",
        security = @SecurityRequirement(name = "Auth Teacher")
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access forbidden"),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    public Response deleteQualification(
        @Parameter(description ="Qualification ID to delete", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }
}