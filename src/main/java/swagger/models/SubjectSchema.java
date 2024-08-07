package swagger.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class SubjectSchema {
    
    private Long id;
    private String name;
    private String groupId;

    @JsonProperty("id")
    @NotNull
    @Schema(example = "4", description = "")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    @NotNull
    @Schema(example = "Mathematics", description = "")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("groupId")
    @NotNull
    @Schema(example = "10", description = "")
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
}
