package org.jog.gitsecuritydb.service.impl;

import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.PermissionDTO;
import org.jog.gitsecuritydb.dto.ShowPermissionDTO;
import org.jog.gitsecuritydb.exception.ObjectNotFoundException;
import org.jog.gitsecuritydb.persistence.entity.security.GrantedPermission;
import org.jog.gitsecuritydb.persistence.entity.security.Operation;
import org.jog.gitsecuritydb.persistence.entity.security.Role;
import org.jog.gitsecuritydb.persistence.repository.security.OperationRepository;
import org.jog.gitsecuritydb.persistence.repository.security.PermissionRepository;
import org.jog.gitsecuritydb.persistence.repository.security.RoleRepository;
import org.jog.gitsecuritydb.service.PermissionService;
import org.jog.gitsecuritydb.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final OperationRepository operationRepository;

    @Override
    public Page<ShowPermissionDTO> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(this::mapEntityToShowDTO);
    }

    @Override
    public Optional<ShowPermissionDTO> findOneById(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .map(this::mapEntityToShowDTO);
    }

    @Override
    public ShowPermissionDTO createOne(PermissionDTO permissionDTO) {

        GrantedPermission newPermission = new GrantedPermission();

        Operation operation = operationRepository.findOperationByName(permissionDTO.getOperation())
                .orElseThrow(() -> new ObjectNotFoundException("Operation not found"));
        newPermission.setOperation(operation);

        Role role = roleRepository.findRoleByName(permissionDTO.getRole())
                .orElseThrow(() -> new ObjectNotFoundException("Role not found"));

        newPermission.setRole(role);

        permissionRepository.save(newPermission);

        return mapEntityToShowDTO(newPermission);
    }

    @Override
    public ShowPermissionDTO deletePermission(Long permissionId) {

        GrantedPermission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ObjectNotFoundException("Permission not found"));

        permissionRepository.delete(permission);

        return mapEntityToShowDTO(permission);
    }

    private ShowPermissionDTO mapEntityToShowDTO(GrantedPermission grantedPermission) {

        ShowPermissionDTO showDTO = new ShowPermissionDTO();

        showDTO.setModule(grantedPermission.getOperation().getModule().getName());
        showDTO.setRole(grantedPermission.getRole().getName());
        showDTO.setOperation(grantedPermission.getOperation().getName());
        showDTO.setHttpMethod(grantedPermission.getOperation().getHttpMethod());

        return showDTO;
    }
}
