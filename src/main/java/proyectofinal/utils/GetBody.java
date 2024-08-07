package proyectofinal.utils;

import java.util.Map;
import java.util.Optional;

public class GetBody {
    private final Optional<Map<String, Object>> body;
    private final String errorMessage;

    private GetBody(Optional<Map<String, Object>> body, String errorMessage) {
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public static GetBody ofBody(Map<String, Object> body) {
        return new GetBody(Optional.ofNullable(body), null);
    }

    public static GetBody ofMessage(String errorMessage) {
        return new GetBody(Optional.empty(), errorMessage);
    }

    public boolean isValid() {
        return body.isPresent();
    }

    public Optional<Map<String, Object>> getBody() {
        return body;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
