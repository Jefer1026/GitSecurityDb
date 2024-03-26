package org.jog.gitsecuritydb.persistence.repository.security;

import org.jog.gitsecuritydb.persistence.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
