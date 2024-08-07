package proyectofinal.datasource.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.dtos.auth.LoginDto;
import proyectofinal.domain.repositories.auth.AuthRepository;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.auth.ErrorsLogin;
import proyectofinal.utils.http.errors.directivo.ErrorsUser;
import proyectofinal.utils.http.status.HttpStatus;
import proyectofinal.utils.security.Cripto;
import proyectofinal.utils.security.Hash;
import proyectofinal.utils.security.JWT;

public class AuthRepositoryImpl extends AuthRepository {

    @Override
    public Map<String, Object> login(LoginDto dto) throws ExceptionHandler {
        final String SQL = "SELECT id, password FROM users u JOIN credentials c ON c.user_id = u.id WHERE c.boleta = ?";

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            String boleta = Cripto.encrypt(dto.getBoleta());
            stm.setString(1, boleta);

            try (ResultSet result = stm.executeQuery()) {

                if (result.next()) {

                    if (Hash.checkPassword(dto.getPassword(), result.getString("password"))) {
                        Map<String, Object> payload = Map.of("id", result.getLong("id"));

                        String token = JWT.generateToken(payload, 180);
                        return Map.of("token", token);
                    }

                    throw new ExceptionHandler(ErrorsLogin.PASSWORD_INCORRECT, HttpStatus.HTTP_FORBIDDEN);
                }

                throw new ExceptionHandler(ErrorsUser.USER_NOT_FOUND, HttpStatus.HTTP_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

}
