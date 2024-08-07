package proyectofinal.datasource.admin.groups;

import java.util.Map;

import proyectofinal.domain.dtos.admin.groups.RegisterGroupDto;
import proyectofinal.domain.dtos.admin.groups.UpdateGroupDto;
import proyectofinal.domain.interfaces.Group;
import proyectofinal.domain.services.admin.GroupsService;
import proyectofinal.utils.http.ExceptionHandler;

public class GroupsServiceImpl extends GroupsService {

    private GroupsRepositoryImpl repository;

    public GroupsServiceImpl(GroupsRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public final Map<String, Object> getGroups() throws ExceptionHandler {
        return repository.getGroups();
    }

    @Override
    public final Map<String, Group> getGroupById(Long id) throws ExceptionHandler {
        return repository.getGroupById(id);
    }

    @Override
    public final Map<String, Group> getGroupByName(String name) throws ExceptionHandler {
        return repository.getGroupByName(name);
    }

    @Override
    public final Map<String, Object> registerGroup(RegisterGroupDto dto) throws ExceptionHandler {
        return repository.registerGroup(dto);
    }

    @Override
    public final Map<String, Object> updatedGroup(UpdateGroupDto dto) throws ExceptionHandler {
        return repository.updatedGroup(dto);
    }

    @Override
    public final Map<String, Object> deleteGroup(Long id) throws ExceptionHandler {
        return repository.deleteGroup(id);
    }

}
