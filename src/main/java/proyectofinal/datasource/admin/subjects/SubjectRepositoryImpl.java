package proyectofinal.datasource.admin.subjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.config.EnvsAdapter;

import proyectofinal.datasource.admin.groups.GroupsServiceImpl;
import proyectofinal.datasource.admin.users.UserServiceImpl;
import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.domain.dtos.admin.subject.*;
import proyectofinal.domain.dtos.admin.subject_user.GetUserToSubjectDto;
import proyectofinal.domain.interfaces.*;

import proyectofinal.domain.repositories.admin.SubjectRepository;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsGroup;
import proyectofinal.utils.http.errors.directivo.ErrorsSubject;
import proyectofinal.utils.http.status.HttpStatus;

public class SubjectRepositoryImpl extends SubjectRepository {

    private GroupsServiceImpl groupService;
    private UserServiceImpl userService;

    public SubjectRepositoryImpl(GroupsServiceImpl groupService, UserServiceImpl userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public final Map<String, Object> getSubjects() throws ExceptionHandler {
        List<Subject> subjects = new ArrayList<Subject>();
        final String SQL = "SELECT * FROM subjects";

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);
                ResultSet result = stm.executeQuery();

        ) {

            while (result.next()) {
                Subject subject = new Subject(result.getString("name"), result.getLong("id"));
                subjects.add(subject);
            }

            return Map.of("subjects", subjects);
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public final Map<String, Subject> getSubjectById(Long id) throws ExceptionHandler {
        final String SQL = "SELECT * FROM subjects WHERE id = ?";
        Subject subject = new Subject();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, id);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    subject.setId(rs.getLong("id"));
                    subject.setName(rs.getString("name"));
                    subject.setGroupId(rs.getLong("group_id"));
                    return Map.of("subject", subject);
                }

                throw new ExceptionHandler(ErrorsSubject.SUBJECT_NOT_FOUND, HttpStatus.HTTP_NOT_FOUND);
            }
        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public final Map<String, Subject> getSubjectByName(String name) throws ExceptionHandler {
        final String SQL = "SELECT * FROM subjects WHERE name = ?";
        Subject subject = new Subject();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setString(1, name);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    subject.setGroupId(rs.getLong("group_id"));
                    subject.setId(rs.getLong("id"));
                    subject.setName(rs.getString("name"));
                }

            }
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("subject", subject);
    }

    @Override
    protected Map<String, String> registerSubject(RegisterSubjectDto dto) throws ExceptionHandler {
        final String SQLSUBJECT = "INSERT INTO subjects (name, group_id) VALUES (?, ?)";
        final String SQLSUBJECT_TEACHER = "INSERT INTO subject_teacher (subject_id, teacher_id) VALUES (currval('subjects_id_seq'), ?)";

        Map<String, Group> group = groupService.getGroupByName(dto.getGroupName());
        if (group.get("group").getId() == null)
            throw new ExceptionHandler(ErrorsSubject.SUBJECT_NOT_FOUND, HttpStatus.HTTP_BAD_REQUEST);

        Map<String, Subject> subjectExist = getSubjectByName(dto.getName());
    
        if(subjectExist.get("subject").getGroupId() != null && subjectExist.get("subject").getGroupId() == group.get("group").getId() )
            throw new ExceptionHandler(ErrorsSubject.SUBJECT_ALREDY_EXIST, HttpStatus.HTTP_BAD_REQUEST);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmSubject = conn.prepareStatement(SQLSUBJECT);

        ) {
            stmSubject.setString(1, dto.getName());
            stmSubject.setLong(2, group.get("group").getId());
            stmSubject.executeUpdate();

            if (dto.getTeacherFullName() != null) {
                Map<String, User> user = userService.getUserByFullNameAndRole(dto.getTeacherFullName(), EnvsAdapter.ROLE_TEACHER);

                try (

                        PreparedStatement stmSubjectTeacher = conn.prepareStatement(SQLSUBJECT_TEACHER);

                ) {

                    stmSubjectTeacher.setLong(1, user.get("teacher").getId());
                    stmSubjectTeacher.executeUpdate();

                }
            }

            return Map.of("message", "Subject created successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, String> updateSubject(UpdateSubjectDto dto) throws ExceptionHandler {
        final String NEW_TEACHER = "INSERT INTO subject_teacher (teacher_id, subject_id) VALUES (?, ?)";
        final String UPDATE_TEACHER = "UPDATE subject_teacher SET teacher_id = ? WHERE subject_id = ?";
        final String SQL = "UPDATE subjects SET name = ?, group_id = ? WHERE id = ?";
        
        Map<String, Subject> subjectExistById = getSubjectById(dto.getId());
        
        String name = dto.getName() == null ? subjectExistById.get("subject").getName() : dto.getName();
        Long groupId = subjectExistById.get("subject").getGroupId();

        if(dto.getGroupName() != null ){
            Map<String, Group> groupExist = groupService.getGroupByName(dto.getGroupName());
            if(groupExist.get("group").getId() == null)
                throw new ExceptionHandler(ErrorsGroup.GROUP_NOT_FOUND, HttpStatus.HTTP_NOT_FOUND);

            groupId = groupExist.get("group").getId();
        }

        Map<String, Subject> subjectExist = getSubjectByNameAndGroup(name, groupId);

        if(subjectExist.get("subject").getId() != null){

            if(subjectExist.get("subject").getId() != subjectExistById.get("subject").getId())
                throw new ExceptionHandler(ErrorsSubject.SUBJECT_ALREDY_EXIST, HttpStatus.HTTP_BAD_REQUEST);
                    
        }
        try(

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setString(1, name);
            stm.setLong(2, groupId);
            stm.setLong(3, dto.getId());
            stm.executeUpdate();

            if (dto.getTeacherFullName() != null) {
                Map<String, User> user = userService.getUserByFullNameAndRole(dto.getTeacherFullName(), EnvsAdapter.ROLE_TEACHER);
                Map<String, SubjectUser> subjectUser = getSubjectUserBySubjectId(dto.getId());
                
                final String SQLTEACHER= subjectUser.get("subject_user").getUserId() != null ? UPDATE_TEACHER : NEW_TEACHER;
              
                try (

                    PreparedStatement stmSubjectTeacher = conn.prepareStatement(SQLTEACHER);

                ) {

                    stmSubjectTeacher.setLong(1, user.get("teacher").getId());
                    stmSubjectTeacher.setLong(2, dto.getId());
                    stmSubjectTeacher.executeUpdate();

                }
            }

            return Map.of("message", "Subject updated successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, String> registerStudentToSubject(RegisterStudentDto dto) throws ExceptionHandler {
        final String SQL = "INSERT INTO subject_student (student_id, subject_id) VALUES (?, ?)";
        
        getSubjectById(dto.getSubjectId());
        Map<String, User> user = userService.getUserByFullNameAndRole(dto.getFullname(), EnvsAdapter.ROLE_STUDENT);

        ResultDto<GetUserToSubjectDto> resultDto = GetUserToSubjectDto.validateParams(user.get("student").getId().toString(), dto.getSubjectId().toString());
        Map<String, SubjectUser> userSubject = getStudentToSubject(resultDto.getDto());
        if(userSubject.get("subject_user").getUserId() != null)
            throw new ExceptionHandler(ErrorsSubject.RECORD_STUDENT_EXIST, HttpStatus.HTTP_BAD_REQUEST);
        
        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, user.get("student").getId());
            stm.setLong(2, dto.getSubjectId());
            stm.executeUpdate();

            return Map.of("message", "Student register to the subect successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    

    @Override
    protected Map<String, String> deleteStudentToSubject(GetUserToSubjectDto dto)throws ExceptionHandler {
        final String SQL = "DELETE FROM subject_student WHERE subject_id = ? AND student_id = ?";
    
        Map<String, SubjectUser> subjectStudent = getStudentToSubject(dto);
        if(subjectStudent.get("subject_user").getUserId() == null)
            throw new ExceptionHandler(ErrorsSubject.RECORD_STUDENT_SUBJECT, HttpStatus.HTTP_NOT_FOUND);
    
        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, dto.getSubjectId());
            stm.setLong(2, dto.getUserId());
            stm.executeUpdate();

            return Map.of("message", "Student deleted to the subject successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }
    

    @Override
    protected Map<String, String> deleteTeacherToSubject(GetUserToSubjectDto dto)throws ExceptionHandler {
        final String SQL = "DELETE FROM subject_teacher WHERE subject_id = ? AND teacher_id = ?";

        Map<String, SubjectUser> subjectTeacher = getTeacherToSubject(dto);
        if(subjectTeacher.get("subject_user").getUserId() == null)
            throw new ExceptionHandler(ErrorsSubject.RECORD_TEACHER_SUBJECT, HttpStatus.HTTP_NOT_FOUND);
        
        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, dto.getSubjectId());
            stm.setLong(2, dto.getUserId());
            stm.executeUpdate();

            return Map.of("message", "Teacher deleted to the subject successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, String> deleteSubject(Long id) throws ExceptionHandler {
        final String SQL = "DELETE FROM subjects WHERE id = ?";
        getSubjectById(id);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, id);
            stm.executeUpdate();

            return Map.of("message", "Subject deleted successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    public Map<String, SubjectUser> getStudentToSubject(GetUserToSubjectDto dto)throws ExceptionHandler {
        final String SQL = "SELECT * FROM subject_student WHERE subject_id = ? AND student_id = ?";
        SubjectUser subjectUser = new SubjectUser();
        
        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, dto.getSubjectId());
            stm.setLong(2, dto.getUserId());

            try(ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    subjectUser.setSubjectId(rs.getLong("subject_id"));
                    subjectUser.setUserId(rs.getLong("student_id"));
                }

            }
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("subject_user", subjectUser);
    }

    protected Map<String, SubjectUser> getTeacherToSubject(GetUserToSubjectDto dto)throws ExceptionHandler {
        final String SQL = "SELECT * FROM subject_teacher WHERE subject_id = ? AND teacher_id = ?";
        SubjectUser subjectUser = new SubjectUser();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, dto.getSubjectId());
            stm.setLong(2, dto.getUserId());

            try(ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    subjectUser.setSubjectId(rs.getLong("subject_id"));
                    subjectUser.setUserId(rs.getLong("teacher_id"));
                }

            }
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
        return Map.of("subject_user", subjectUser);
    }

    private Map<String, Subject> getSubjectByNameAndGroup(String name, Long groupId) throws ExceptionHandler{
        final String SQL = "SELECT * FROM subjects WHERE name = ? AND group_id = ?";
        Subject subject = new Subject();

        try (

            Connection connection = ConectDBAdapter.getConnection();
            PreparedStatement stm =  connection.prepareStatement(SQL);

        ){
            
            stm.setString(1, name);
            stm.setLong(2, groupId);

            try(
                ResultSet rs = stm.executeQuery();
            ){
                if(rs.next()){
                    subject.setGroupId(rs.getLong("group_id"));
                    subject.setId(rs.getLong("id"));
                    subject.setName(rs.getString("name"));
                }
            }

        } catch (Exception e) { 
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
        return Map.of("subject", subject);
    }

    private Map<String, SubjectUser> getSubjectUserBySubjectId(Long subjectId) throws ExceptionHandler{
        final String SQL = "SELECT * FROM subject_teacher WHERE subject_id = ?";
        SubjectUser subject = new SubjectUser();
        try (

            Connection conn = ConectDBAdapter.getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);
        ){
            stm.setLong(1, subjectId);

            try(ResultSet rs = stm.executeQuery()){

                if(rs.next()){
                    subject.setSubjectId(rs.getLong("subject_id"));
                    subject.setUserId(rs.getLong("teacher_id"));
                }

            }

        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
        
        return Map.of("subject_user", subject);
    }
}
