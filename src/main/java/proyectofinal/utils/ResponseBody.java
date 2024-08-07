package proyectofinal.utils;

import com.google.gson.Gson;
import spark.Response;

public class ResponseBody {
    private static final Gson gson = new Gson();

    public static String jsonResponse(Response res, Object data, Integer status) {
        res.status(status);
        return jsonResponse(res, data);
    }

    public static String jsonResponse(Response res, Object data) {
        res.type("application/json");
        return gson.toJson(data);
    }
}
