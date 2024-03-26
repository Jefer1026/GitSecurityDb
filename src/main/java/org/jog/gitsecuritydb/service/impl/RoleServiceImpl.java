package org.jog.gitsecuritydb.service.impl;

import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.persistence.entity.security.Role;
import org.jog.gitsecuritydb.persistence.repository.security.RoleRepository;
import org.jog.gitsecuritydb.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Value("${security.default.role}")
    String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {

        return roleRepository.findRoleByName(defaultRole);
    }

}
