package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.entity.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User save(User user);

    /**
     * Add new user to db
     * @param user
     * @return
     */
    User addNewUser(User user);

    User findByUsername(String username);

    List<User> getUsers();

    User findById(UUID id);

    User toUser(UserDto userDto);

    Boolean existsByUsername(String username);

//    Boolean existsByUsername(String username);
}
