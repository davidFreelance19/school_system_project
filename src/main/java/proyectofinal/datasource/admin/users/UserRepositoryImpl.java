package proyectofinal.datasource.admin.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proyectofinal.domain.dtos.admin.user.CreateUserDto;
import proyectofinal.domain.dtos.admin.user.UpdateUserDto;
import proyectofinal.domain.interfaces.User;
import proyectofinal.domain.repositories.admin.UserRepository;
import proyectofinal.utils.email.SendEmail;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsUser;
import proyectofinal.utils.http.status.HttpStatus;

import proyectofinal.utils.security.*;
import proyectofinal.config.*;

public class UserRepositoryImpl extends UserRepository {

    @Override
    protected Map<String, Object> registerUser(CreateUserDto createUserDto, String ROLE) throws ExceptionHandler {
        final String SQLUSER = "INSERT INTO users (fullname, email, role) VALUES (?,?,?)";
        final String SQLCREDENTIALS = "INSERT INTO credentials (user_id, password, boleta) VALUES (currval('user_id_seq'),?,?)";

        if (getUserByEmail(createUserDto.getEmail()).getId() != null)
            throw new ExceptionHandler(ErrorsUser.USER_ALREDY_EXIST, HttpStatus.HTTP_BAD_REQUEST);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmtUser = conn.prepareStatement(SQLUSER)

        ) {
            String emailEncrypt = Cripto.encrypt(createUserDto.getEmail());
            String roleEncrypt = Cripto.encrypt(ROLE);

            stmtUser.setString(1, createUserDto.getFullname());
            stmtUser.setString(2, emailEncrypt);
            stmtUser.setString(3, roleEncrypt);
            stmtUser.executeUpdate();

            try (PreparedStatement stmtCredentials = conn.prepareStatement(SQLCREDENTIALS)) {

                String boleta = GenerateCredentials.generateBoleta();
                String password = GenerateCredentials.generatePassword(createUserDto.getFullname());

                stmtCredentials.setString(1, Hash.hashPassword(password));
                stmtCredentials.setString(2, Cripto.encrypt(boleta));
                stmtCredentials.executeUpdate();

                SendEmail.sendEmailRegisterAccount(createUserDto.getEmail(), password, boleta);
            } catch (Exception e) {
                throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
            }
            String tagUser = ROLE.equals(EnvsAdapter.ROLE_STUDENT) ? "Student" : "Teacher";
            return Map.of("message", tagUser + " created successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, Object> getUsersByRole(String typeUser) throws ExceptionHandler {
        final String SQL = "SELECT * FROM users WHERE role = ?";
        List<User> students = new ArrayList<User>();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {

            String roleEncrypt = Cripto.encrypt(typeUser);
            stmt.setString(1, roleEncrypt);

            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    User user = new User(result.getLong("id"), result.getString("fullname"));
                    students.add(user);
                }
            }

            String tagUser = typeUser.equals(EnvsAdapter.ROLE_STUDENT) ? "students" : "teachers";
            return Map.of(tagUser, students);
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, User> getUserByIdAndRole(Long id, String typeUser) throws ExceptionHandler {
        final String SQL = "SELECT * FROM users WHERE id = ? AND role = ?";
        User user = new User();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {
            String roleEncrypt = Cripto.encrypt(typeUser);

            stmt.setLong(1, id);
            stmt.setString(2, roleEncrypt);
            try (ResultSet result = stmt.executeQuery()) {

                if (result.next()) {
                    user.setId(result.getLong("id"));
                    user.setFullname(result.getString("fullname"));
                    user.setEmail(result.getString("email"));
                    String tagUser = typeUser.equals(EnvsAdapter.ROLE_STUDENT) ? "student" : "teacher";
                    return Map.of(tagUser, user);
                }

                throw new ExceptionHandler(ErrorsUser.USER_NOT_FOUND, HttpStatus.HTTP_NOT_FOUND);
            }

        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

    }

    @Override
    protected Map<String, User> getUserByFullNameAndRole(String fullname, String typeUser) throws ExceptionHandler {
        final String SQL = "SELECT * FROM users WHERE fullname = ? AND role = ?";
        User user = new User();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {
            String roleEncrypt = Cripto.encrypt(typeUser);

            stmt.setString(1, fullname);
            stmt.setString(2, roleEncrypt);
            try (ResultSet result = stmt.executeQuery()) {

                if (result.next()) {
                    user.setId(result.getLong("id"));
                    user.setFullname(result.getString("fullname"));
                    String tagUser = typeUser.equals(EnvsAdapter.ROLE_STUDENT) ? "student" : "teacher";
                    return Map.of(tagUser, user);
                }

                throw new ExceptionHandler(ErrorsUser.USER_NOT_FOUND, HttpStatus.HTTP_NOT_FOUND);
            }

        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

    }

    protected User getUserByEmail(String email) throws ExceptionHandler {
        final String SQL = "SELECT * FROM users WHERE email = ?";
        User user = new User();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {
            String emailEncrypt = Cripto.encrypt(email);
            stmt.setString(1, emailEncrypt);

            try (ResultSet result = stmt.executeQuery()) {

                if (result.next()) {
                    user.setId(result.getLong("id"));
                    user.setFullname(result.getString("fullname"));
                }

            }

        } catch (SQLException e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return user;
    }

    @Override
    protected Map<String, Object> updateUser(UpdateUserDto dto, String typeUser) throws ExceptionHandler {
        final String SQL = "UPDATE users SET fullname = ?, email = ? WHERE id = ? AND role = ?";

        Map<String, User> user = getUserByIdAndRole(dto.getId(), typeUser);
        String tagUser = typeUser.equals(EnvsAdapter.ROLE_STUDENT) ? "student" : "teacher";
        String email = "";

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {

            if (dto.getEmail() != null) {
                User userExist = getUserByEmail(dto.getEmail());

                if (userExist.getId() != null && userExist.getId() != dto.getId())
                    throw new ExceptionHandler(ErrorsUser.USER_ALREDY_EXIST, HttpStatus.HTTP_BAD_REQUEST);

                email = Cripto.encrypt(dto.getEmail());
            } else {
                email = user.get(tagUser).getEmail();
            }

            String roleEncrypt = Cripto.encrypt(typeUser);
            String fullname = dto.getFullname() == null ? user.get(tagUser).getFullname() : dto.getFullname();

            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setLong(3, dto.getId());
            stmt.setString(4, roleEncrypt);
            stmt.executeUpdate();

            return Map.of("message", tagUser + " updated succesfully");
        }catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    protected Map<String, Object> deleteUser(Long id, String typeUser) throws ExceptionHandler {
        final String SQL = "DELETE FROM users WHERE role = ? AND id = ?";
        getUserByIdAndRole(id, typeUser);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL);

        ) {
            stmt.setString(1, typeUser);
            stmt.setLong(2, id);
            stmt.executeUpdate();

            String tagUser = typeUser.equals(EnvsAdapter.ROLE_STUDENT) ? "Student" : "Teacher";
            return Map.of("message", tagUser + " deleted succesfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }
}
