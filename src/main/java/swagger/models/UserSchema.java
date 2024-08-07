package swagger.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserSchema {
    
    private Long id;
    private String email;
    private String fullname;
    private String role;
    
    @NotNull
    @JsonProperty("id")
    @Schema(example = "10")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @JsonProperty("email")
    @Schema(example = "email@emailexample.com")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @JsonProperty("fullname")
    @Schema(example = "Jon Doe")
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @NotNull
    @JsonProperty("role")
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
}
