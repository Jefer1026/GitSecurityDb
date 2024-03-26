package org.jog.gitsecuritydb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.PermissionDTO;
import org.jog.gitsecuritydb.dto.ShowPermissionDTO;
import org.jog.gitsecuritydb.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {


    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<ShowPermissionDTO>> findAll(Pageable pageable) {

        Page<ShowPermissionDTO> permissions = permissionService.findAll(pageable);

        return permissions.hasContent()
                ? ResponseEntity.ok(permissions)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermissionDTO> findOneById(@PathVariable Long permissionId) {
        Optional<ShowPermissionDTO> permission = permissionService.findOneById(permissionId);

        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShowPermissionDTO> createOne(@RequestBody @Valid PermissionDTO permissionDTO) {

        ShowPermissionDTO permission = permissionService.createOne(permissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(permission);

    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ShowPermissionDTO> deleteById(@PathVariable Long permissionId) {
        ShowPermissionDTO permission = permissionService.deletePermission(permissionId);
        return ResponseEntity.ok(permission);
    }
}
