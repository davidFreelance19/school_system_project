package proyectofinal.presentation.router.teacher;

import static spark.Spark.*;

import proyectofinal.config.EnvsAdapter;
import proyectofinal.datasource.admin.subject_user.SubjectUserRepositoryImpl;
import proyectofinal.datasource.admin.subject_user.SubjectUserServiceImpl;
import proyectofinal.datasource.teacher.TeacherRepositoryImpl;
import proyectofinal.datasource.teacher.TeacherServiceImpl;
import proyectofinal.presentation.controllers.teacher.TeacherController;
import proyectofinal.presentation.middlewares.auth.AuthMiddleware;
import proyectofinal.presentation.middlewares.teacher.AccessByQualificationId;
import proyectofinal.presentation.middlewares.teacher.AcessToSubjectById;
import spark.Request;
import spark.Response;

public class TeacherRoutes {

    public static void routes() {
        SubjectUserRepositoryImpl subjectUserRepository = new SubjectUserRepositoryImpl();
        SubjectUserServiceImpl subjectUserService = new SubjectUserServiceImpl(subjectUserRepository);

        TeacherRepositoryImpl repository = new TeacherRepositoryImpl(subjectUserService);
        TeacherServiceImpl service = new TeacherServiceImpl(repository);
        
        TeacherController controller = new TeacherController(service);

        before("/*", AuthMiddleware::auth);
        before( "/*", (Request req, Response res) -> AuthMiddleware.checkIsCorrectRole(req, res, EnvsAdapter.ROLE_TEACHER));
        
        before("/subjects/:subjectId", AcessToSubjectById::validateAccessBySubjectId);
        before("/subjects/register-qualification/:subjectId", AcessToSubjectById::validateAccessBySubjectId);
        
        before("/subjects/delete-qualification/:id", AccessByQualificationId::validateAccess);
        before("/subjects/update-qualification/:id", AccessByQualificationId::validateAccess);

        get("/subjects", controller::getSubjectsToTeacher);
        get("/subjects/:subjectId", controller::getSubjectById);
        post("/subjects/register-qualification/:subjectId", controller::registerQualification);
        patch("/subjects/update-qualification/:id", controller::updateQualification);
        delete("/subjects/delete-qualification/:id", controller::deleteQualification);
    }
}
