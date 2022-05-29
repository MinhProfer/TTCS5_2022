package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByRoleName(String roleName);

    Role save(Role role);

    void saveAll(List<Role> roles);
}
