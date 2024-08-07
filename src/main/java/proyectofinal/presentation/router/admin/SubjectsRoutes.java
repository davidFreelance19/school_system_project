package proyectofinal.presentation.router.admin;
import static spark.Spark.*;
import proyectofinal.datasource.admin.groups.GroupsRepositoryImpl;
import proyectofinal.datasource.admin.groups.GroupsServiceImpl;
import proyectofinal.datasource.admin.subjects.SubjectRepositoryImpl;
import proyectofinal.datasource.admin.subjects.SubjectServiceImpl;
import proyectofinal.datasource.admin.users.UserRepositoryImpl;
import proyectofinal.datasource.admin.users.UserServiceImpl;
import proyectofinal.presentation.controllers.Admin.SubjectsController;

public class SubjectsRoutes {
     public static void routes() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        GroupsRepositoryImpl repositoryGroups = new GroupsRepositoryImpl();
        GroupsServiceImpl serviceGroups = new GroupsServiceImpl(repositoryGroups);

        SubjectRepositoryImpl repository = new SubjectRepositoryImpl(serviceGroups, userService);
        SubjectServiceImpl service = new SubjectServiceImpl(repository);
        SubjectsController controller = new SubjectsController(service);

        get("/", controller::getSubjects);
        get("/get-subject/:id", controller::getSubjectById);
        get("/get-subject", controller::getSubjectByName);
        post("/", controller::registerSubject);
        put("/:id", controller::updateSubject);
        post("/register-student/:id", controller::registerStudentToSubject);
        delete("/remove-student", controller::deleteStudentToSubject);
        delete("/remove-teacher", controller::deleteTeacherToSubject);
        delete("/:id", controller::deleteSubject);             
    }
}
