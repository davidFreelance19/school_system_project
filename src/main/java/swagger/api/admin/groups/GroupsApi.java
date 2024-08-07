package swagger.api.admin.groups;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import swagger.models.GroupSchema;

@Tag(name = "Admin - Groups", description = "CRUD of groups for the school system")
@Path("admin/groups")
public class GroupsApi {
    
    @GET
    @Path("//")
    @Produces({ "application/json" })
    @Operation(
        summary = "Obtener todos los grupos registrados", 
        operationId = "getGroups",
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
                                    "groups": [
                                        {
                                            "id": 1,
                                            "name": "Group 1"
                                        },
                                        {
                                            "id": 2,
                                            "name": "Group 2"
                                        }
                                    ]
                                }
                            """
                )
            )    
        ),
        @ApiResponse(responseCode = "401", description = "Access unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getGroups() {
        return Response.ok().build();
    }

    @GET
    @Path("/get-group/{id}")
    @Produces({ "application/json"})
    @Operation(summary = "Get information a group by id", operationId = "getGroupById", security = @SecurityRequirement(name = "Auth Admin"))
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
                                    "group": {
                                        "id": 1,
                                        "name": "Group by ID 1"
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Group not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getGroupById(
        @Parameter(description = "Id of the group to retrieve", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @GET
    @Path("/get-group/")
    @Produces({ "application/json"})
    @Operation(summary = "Get information a group by name", operationId = "getGroupByName", security = @SecurityRequirement(name = "Auth Admin"))
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
                                    "group": {
                                        "id": 1,
                                        "name": "Group by ID 1"
                                    }
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Group not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getGroupByName(
        @Parameter(description = "Name of the group to retrieve", required = true)
        @QueryParam("name") String name
    ){
        return Response.ok().build();
    }

    @POST
    @Path("//")
    @Produces({ "application/json"})
    @Consumes({ "application/json" })
    @Operation(summary = "Register a group.", operationId = "registerGroup", security = @SecurityRequirement(name = "Auth Admin"))
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
                                   "message": "Group created successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response registerGroup(@FormParam(value = "name") String name){
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Produces({ "application/json"})
    @Consumes({ "application/json" })
    @Operation(summary = "Update properties a group.", operationId = "updateGroup", security = @SecurityRequirement(name = "Auth Admin"))
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
                                   "message": "Group updated successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Group not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response updateGroup(
        @FormParam(value = "name") String name,
        @Parameter(description = "ID of the group to update", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({ "application/json"})
    @Operation(summary = "Delete a group by id", operationId = "deleteGroup", security = @SecurityRequirement(name = "Auth Admin"))
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Operation success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                                {
                                   "message": "Group deleted successfully"
                                }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access unauthorized"),
            @ApiResponse(responseCode = "404", description = "Group not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response deleteGroup(
        @Parameter(description = "ID of the group to delete", required = true)
        @PathParam("id") String id
    ){
        return Response.ok().build();
    }
}
