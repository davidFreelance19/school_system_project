package proyectofinal.domain.interfaces;

public class Subject {
    
    private Long id;
    private String name;
    private Long groupId;
    
    public Subject() {
    }
    
    public Subject(String name) {
        this.name = name;
    }
    
    public Subject(Long groupId) {
        this.groupId = groupId;
    }

    public Subject(String name, Long groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
}
