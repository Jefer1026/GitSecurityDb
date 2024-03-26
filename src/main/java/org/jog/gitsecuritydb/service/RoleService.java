package org.jog.gitsecuritydb.service;

import org.jog.gitsecuritydb.persistence.entity.security.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findDefaultRole();

}
