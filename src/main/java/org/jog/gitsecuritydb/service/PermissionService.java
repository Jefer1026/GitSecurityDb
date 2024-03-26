package org.jog.gitsecuritydb.service;

import org.jog.gitsecuritydb.dto.PermissionDTO;
import org.jog.gitsecuritydb.dto.ShowPermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PermissionService {

    Page<ShowPermissionDTO> findAll(Pageable pageable);

    Optional<ShowPermissionDTO> findOneById(Long permissionId);

    ShowPermissionDTO createOne(PermissionDTO permissionDTO);

    ShowPermissionDTO deletePermission(Long permissionId);
}
