package proyectofinal.utils.http.errors.directivo;

public class ErrorsSubject {
    public static final String SUBJECT_ALREDY_EXIST = "The subject in this group already exists";
    public static final String SUBJECT_NAME_REQUIRED = "The subject name is required";
    public static final String SUBJECT_NAME_INVALID = "The subject name is invalid";

    public static final String GROUP_NAME_REQUIRED = "The group name is required";
    public static final String GROUP_NAME_INVALID = "The group name is invalid";

    public static final String TEACHER_NAME_INVALID = "The teacher fullname is invalid";

    public static final String SUBJECT_NOT_FOUND = "The subject does not exist";
    public static final String RECORD_STUDENT_EXIST = "The student is already registered in this subject.";
    public static final String RECORD_STUDENT_NOT_EXIST = "The student does not registered in this subject.";
    public static final String RECORD_STUDENT_SUBJECT = "Record with provided student ID and subject ID does not exist";
    public static final String RECORD_TEACHER_SUBJECT = "Record with provided teacher ID and subject ID does not exist";

    public static final String STUDENTNAME_REQUIRED = "The student fullname is required";
    public static final String STUDENTNAME_INVALID = "The student fullname is invalid";

    public static final String USER_ID_REQUIRED = "The user id is required";
    public static final String SUBJECT_ID_REQUIRED = "The subject id is required";
    public static final String QUERY_PARAMS_INVALID = "The user id or subject id is invalid";

}
