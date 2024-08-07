package proyectofinal.domain.dtos.admin.user;

import java.util.Map;
import java.util.Optional;

import proyectofinal.domain.dtos.ResultDto;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsUser;

public class UpdateUserDto {
    private Long id;
    private String fullname;
    private String email;

    public UpdateUserDto() {
    }

    public Long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    private void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public static ResultDto<UpdateUserDto> update(Optional<Map<String, Object>> props, Long id) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setId(id);

        if (!props.isPresent())
            return new ResultDto<UpdateUserDto>(ErrorsShared.BODY_EMPTY_ERROR, null);

        if (!props.get().containsKey("fullname") && !props.get().containsKey("email"))
            return new ResultDto<UpdateUserDto>(null, updateUserDto);

        if (props.get().containsKey("fullname") && !(props.get().get("fullname") instanceof String))
            return new ResultDto<UpdateUserDto>(ErrorsUser.NAME_NOT_VALID, null);

        if(props.get().containsKey("fullname") && props.get().get("fullname").toString().trim().length() == 0)
            return new ResultDto<UpdateUserDto>(ErrorsUser.NAME_IS_REQUIRED, null);

        if(props.get().containsKey("fullname")){
            String fullname = props.get().get("fullname").toString().trim();
            updateUserDto.setFullname(fullname);
        }

        if (props.get().containsKey("email") && !(props.get().get("email") instanceof String))
            return new ResultDto<UpdateUserDto>(ErrorsUser.EMAIL_NOT_VALID, null);

        if (props.get().containsKey("email") && props.get().get("email").toString().trim().length() == 0)
            return new ResultDto<UpdateUserDto>(ErrorsUser.EMAIL_IS_REQUIRED, null);
            
        if(props.get().containsKey("email")){
            String email = props.get().get("email").toString().trim();
            updateUserDto.setEmail(email);
        }
        
        return new ResultDto<UpdateUserDto>(null, updateUserDto);
    }

}
