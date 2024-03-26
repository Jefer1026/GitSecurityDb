package org.jog.gitsecuritydb.persistence.repository.security;

import org.jog.gitsecuritydb.persistence.entity.security.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<GrantedPermission,Long> {
}
