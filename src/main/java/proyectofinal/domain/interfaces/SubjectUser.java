package proyectofinal.domain.interfaces;

public class SubjectUser {
    private Long userId;
    private Long subjectId;

    public SubjectUser() {
    }
    
    public Long getUserId() {
        return userId;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

}
