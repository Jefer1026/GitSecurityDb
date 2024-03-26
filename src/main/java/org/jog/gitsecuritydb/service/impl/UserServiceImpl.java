package org.jog.gitsecuritydb.service.impl;

import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.UserDTO;
import org.jog.gitsecuritydb.exception.InvalidPasswordException;
import org.jog.gitsecuritydb.exception.ObjectNotFoundException;
import org.jog.gitsecuritydb.persistence.entity.security.Role;
import org.jog.gitsecuritydb.persistence.entity.security.User;
import org.jog.gitsecuritydb.persistence.repository.security.UserRepository;
import org.jog.gitsecuritydb.service.RoleService;
import org.jog.gitsecuritydb.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User registeredOneCustomer(UserDTO userDTO) {
        validationPassword(userDTO);

        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role defautlRole = roleService.findDefaultRole()
                .orElseThrow(() -> new ObjectNotFoundException("Role not found"));

        user.setRole(defautlRole);


        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private void validationPassword(UserDTO userDTO) {
        if (!StringUtils.hasText(userDTO.getPassword()) || (!StringUtils.hasText(userDTO.getRepeatPassword()))) {
            throw new InvalidPasswordException("Password is required");
        }
        if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
            throw new InvalidPasswordException("User don't match");
        }
    }
}
