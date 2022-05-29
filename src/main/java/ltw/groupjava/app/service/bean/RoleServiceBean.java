package ltw.groupjava.app.service.bean;

import lombok.AllArgsConstructor;
import ltw.groupjava.app.entity.Role;
import ltw.groupjava.app.repository.RoleRepo;
import ltw.groupjava.app.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceBean implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepo.findByRoleName(roleName).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void saveAll(List<Role> roles) {
        roleRepo.saveAll(roles);
    }
}
