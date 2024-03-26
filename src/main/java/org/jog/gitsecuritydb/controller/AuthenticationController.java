package org.jog.gitsecuritydb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.auth.AuthenticationRequest;
import org.jog.gitsecuritydb.dto.auth.AuthenticationResponse;
import org.jog.gitsecuritydb.persistence.entity.security.User;
import org.jog.gitsecuritydb.service.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isTokenValid = authenticationService.validToken(jwt);

        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {

        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);

        return ResponseEntity.ok(rsp);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile() {
        User user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }
}
