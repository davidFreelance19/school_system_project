package swagger.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class GroupSchema {
    private @Valid Long id;
    private @Valid String name;

    @Schema(example = "10", description = "")
    @NotNull
    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Schema(example = "4AV1", description = "")
    @NotNull
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
