package proyectofinal.domain.interfaces.student;

public class QualificationsStudent {

    private Long subjectId;
    private String subjectName;
    private String teacher;
    private String groupName;
    private Long qualification;
    
    public QualificationsStudent() {
    }
  
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void setQualification(Long qualification) {
        this.qualification = qualification;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getGroupName() {
        return groupName;
    }

    public Long getQualification() {
        return qualification;
    }
    

}
