package proyectofinal.presentation.router.admin;

import static spark.Spark.*;

import proyectofinal.datasource.admin.groups.GroupsRepositoryImpl;
import proyectofinal.datasource.admin.groups.GroupsServiceImpl;
import proyectofinal.presentation.controllers.Admin.GroupsController;

public class GroupsRoutes {

    public static void routes() {

        GroupsRepositoryImpl repository = new GroupsRepositoryImpl();
        GroupsServiceImpl service = new GroupsServiceImpl(repository);
        GroupsController controller = new GroupsController(service);

        get("/", controller::getGroups);
        get("/get-group/:id", controller::getGroupById);
        get("/get-group", controller::getGroupByName);
        post("/", controller::registerGroup);
        put("/:id", controller::updateGroup);
        delete("/:id", controller::deleteGroup);
    }
}
