
package proyectofinal.utils.http;

public class ExceptionHandler extends Exception {
    private int status;

    public ExceptionHandler(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}