package proyectofinal.domain.interfaces;

public class User {

    private Long id;
    private String fullname;
    private String email;
    private String role;

    public User() {
    }

    public User(Long id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public User(Long id, String fullname, String email, String role) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
