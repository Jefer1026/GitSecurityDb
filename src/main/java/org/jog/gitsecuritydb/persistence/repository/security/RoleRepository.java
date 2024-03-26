package org.jog.gitsecuritydb.persistence.repository.security;

import org.jog.gitsecuritydb.persistence.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(String defaultRole);
}
