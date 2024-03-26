package org.jog.gitsecuritydb.service;

import org.jog.gitsecuritydb.dto.UserDTO;
import org.jog.gitsecuritydb.persistence.entity.security.User;

import java.util.Optional;

public interface UserService {

    User registeredOneCustomer(UserDTO userDTO);

    Optional<User> findUserByUsername(String username);
}
