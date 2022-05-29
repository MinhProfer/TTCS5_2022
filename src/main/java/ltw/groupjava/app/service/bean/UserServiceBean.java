package ltw.groupjava.app.service.bean;

import lombok.AllArgsConstructor;
import ltw.groupjava.app.entity.Role;
import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.entity.dto.UserDto;
import ltw.groupjava.app.repository.UserRepo;
import ltw.groupjava.app.service.CartService;
import ltw.groupjava.app.service.RoleService;
import ltw.groupjava.app.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceBean implements UserService {
    private final RoleService roleService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User addNewUser(User user) {
        Role roleUser = roleService.findByRoleName("USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles(new LinkedHashSet<>());
        }
        user.getRoles().add(roleUser);

        user = userRepo.save(user);
        cartService.createNewCartWithUser(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findById(UUID id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User toUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        return user;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

}
