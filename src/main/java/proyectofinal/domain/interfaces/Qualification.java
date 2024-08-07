package proyectofinal.domain.interfaces;

public class Qualification {
    private Long id;
    private Long subjectId;
    private Long studentId;
    private Long qualification;
    
    public Qualification() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Long getQualification() {
        return qualification;
    }
    public void setQualification(Long qualification) {
        this.qualification = qualification;
    }
    
}
