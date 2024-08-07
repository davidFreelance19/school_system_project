package proyectofinal.domain.dtos;

public class ResultDto<T> {

    private final String message;
    private final T dto;

    public ResultDto(String message, T dto) {
        this.message = message;
        this.dto = dto;
    }

    public String getMessage() {
        return message;
    }

    public T getDto() {
        return dto;
    }

}
