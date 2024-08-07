package proyectofinal.datasource.admin.groups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proyectofinal.config.ConectDBAdapter;
import proyectofinal.domain.dtos.admin.groups.RegisterGroupDto;
import proyectofinal.domain.dtos.admin.groups.UpdateGroupDto;
import proyectofinal.domain.repositories.admin.GroupsRepository;
import proyectofinal.utils.http.ExceptionHandler;
import proyectofinal.utils.http.errors.ErrorsShared;
import proyectofinal.utils.http.errors.directivo.ErrorsGroup;
import proyectofinal.utils.http.status.HttpStatus;
import proyectofinal.domain.interfaces.Group;

public class GroupsRepositoryImpl extends GroupsRepository {

    @Override
    public final Map<String, Object> getGroups() throws ExceptionHandler {
        List<Group> groups = new ArrayList<Group>();
        final String SQL = "SELECT * FROM groups";

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);
                ResultSet result = stm.executeQuery();

        ) {

            while (result.next()) {
                Group group = new Group(result.getLong("id"), result.getString("name"));
                groups.add(group);
            }

            return Map.of("groups", groups);
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public final Map<String, Group> getGroupById(Long id) throws ExceptionHandler {
        final String SQL = "SELECT * FROM groups WHERE id = ?";
        Group group = new Group();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, id);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    group.setId(rs.getLong("id"));
                    group.setName(rs.getString("name"));
                    return Map.of("group", group);
                }

                throw new ExceptionHandler(ErrorsGroup.GROUP_NOT_FOUND, HttpStatus.HTTP_NOT_FOUND);
            }
        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public final Map<String, Group> getGroupByName(String name) throws ExceptionHandler {
        final String SQL = "SELECT * FROM groups WHERE name = ?";
        Group group = new Group();

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setString(1, name);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    group.setId(rs.getLong("id"));
                    group.setName(rs.getString("name"));
                }

            }
        }  catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }

        return Map.of("group", group);
    }

    @Override
    public final Map<String, Object> registerGroup(RegisterGroupDto dto) throws ExceptionHandler {
        final String SQL = "INSERT INTO groups (name) VALUES (?)";
        
        Map<String, Group> group =  getGroupByName(dto.getName());
        if(group.get("group").getId() != null)
            throw new ExceptionHandler(ErrorsGroup.GROUP_ALREADY_EXIST, HttpStatus.HTTP_BAD_REQUEST);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setString(1, dto.getName());
            stm.executeUpdate();

            return Map.of("message", "Group created successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public final Map<String, Object> updatedGroup(UpdateGroupDto dto) throws ExceptionHandler {
        final String SQL = "UPDATE groups SET name = ? WHERE id = ?";
        Map<String, Group> group = getGroupById(dto.getId());
        Map<String, Group> groupWithSameName =  getGroupByName(dto.getName());

        if(groupWithSameName.get("group").getId() != null && groupWithSameName.get("group").getId() != dto.getId())
            throw new ExceptionHandler(ErrorsGroup.GROUP_ALREADY_EXIST, HttpStatus.HTTP_BAD_REQUEST);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            String name = dto.getName() == null ? group.get("group").getName() : dto.getName();
            stm.setString(1, name);
            stm.setLong(2, dto.getId());
            stm.executeUpdate();

            return Map.of("message", "Group updated successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public final Map<String, Object> deleteGroup(Long id) throws ExceptionHandler {
        final String SQL = "DELETE FROM groups WHERE id = ?";
        getGroupById(id);

        try (

                Connection conn = ConectDBAdapter.getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);

        ) {
            stm.setLong(1, id);
            stm.executeUpdate();

            return Map.of("message", "Group deleted successfully");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.HTTP_INTERNAL_ERROR);
        }
    }

}
