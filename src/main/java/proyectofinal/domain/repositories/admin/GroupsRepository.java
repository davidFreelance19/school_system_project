package proyectofinal.domain.repositories.admin;

import java.util.Map;

import proyectofinal.domain.dtos.admin.groups.RegisterGroupDto;
import proyectofinal.domain.dtos.admin.groups.UpdateGroupDto;
import proyectofinal.domain.interfaces.Group;
import proyectofinal.utils.http.ExceptionHandler;

public abstract class GroupsRepository {

    protected abstract Map<String, Object> getGroups() throws ExceptionHandler;

    protected abstract Map<String, Group> getGroupById(Long id) throws ExceptionHandler;

    protected abstract Map<String, Group> getGroupByName(String name) throws ExceptionHandler;

    protected abstract Map<String, Object> registerGroup(RegisterGroupDto dto) throws ExceptionHandler;

    protected abstract Map<String, Object> updatedGroup(UpdateGroupDto dto) throws ExceptionHandler;

    protected abstract Map<String, Object> deleteGroup(Long id) throws ExceptionHandler;

}
