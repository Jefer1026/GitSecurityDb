package org.jog.gitsecuritydb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.UserDTO;
import org.jog.gitsecuritydb.dto.UserRegisteredDTO;
import org.jog.gitsecuritydb.service.auth.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<UserRegisteredDTO> registeredOne(@RequestBody @Valid UserDTO userDTO) {

        UserRegisteredDTO registeredDTO = authenticationService.registerOneCustomer(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredDTO);

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(Arrays.asList());
    }


}
