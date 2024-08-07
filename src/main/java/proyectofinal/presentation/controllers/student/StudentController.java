package proyectofinal.presentation.controllers.student;

import java.util.Map;

import proyectofinal.datasource.student.StudentServiceImpl;
import proyectofinal.domain.interfaces.User;
import proyectofinal.utils.ResponseBody;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.status.HttpStatus;
import spark.Request;
import spark.Response;

public class StudentController {

    private StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    public String getStudent(Request req, Response res) {
        // service.getStudent();
        User user = (User) req.attribute("user");

        try {
            Map<String, Object> response = service.getQualificationsStudent(user.getId());
            return ResponseBody.jsonResponse(res, response, HttpStatus.HTTP_OK);
        } catch (ExceptionHandler e) {
            return ResponseBody.jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }
}
