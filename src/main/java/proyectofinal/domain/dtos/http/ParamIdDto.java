package proyectofinal.domain.dtos.http;

import proyectofinal.domain.dtos.ResultDto;

public class ParamIdDto {
    private final Long param;

    private ParamIdDto(Long param) {
        this.param = param;
    }

    public Long getParam() {
        return param;
    }

    public static ResultDto<ParamIdDto> getParam(String param) {
        try {
            Long paramObj = Long.parseLong(param);
            return new ResultDto<ParamIdDto>(null, new ParamIdDto(paramObj));
        } catch (Exception e) {
            return new ResultDto<ParamIdDto>("Param is not valid", null);
        }
    }
}
