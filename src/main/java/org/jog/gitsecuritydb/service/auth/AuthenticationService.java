package org.jog.gitsecuritydb.service.auth;

import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.UserDTO;
import org.jog.gitsecuritydb.dto.UserRegisteredDTO;
import org.jog.gitsecuritydb.dto.auth.AuthenticationRequest;
import org.jog.gitsecuritydb.dto.auth.AuthenticationResponse;
import org.jog.gitsecuritydb.exception.ObjectNotFoundException;
import org.jog.gitsecuritydb.persistence.entity.security.User;
import org.jog.gitsecuritydb.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getUsername());
        extraClaims.put("role", user.getRole());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );

        authenticationManager.authenticate(authentication);

        User user = userService.findUserByUsername(request.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);

        return authRsp;
    }


    public boolean validToken(String jwt) {
        try {
            jwtService.extractSubject(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User findLoggedInUser() {

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) authToken.getPrincipal();

        return userService.findUserByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found "+username));

    }

    public UserRegisteredDTO registerOneCustomer(UserDTO userDTO) {
        User user = userService.registeredOneCustomer(userDTO);

        UserRegisteredDTO userRegistered = new UserRegisteredDTO();
        userRegistered.setId(user.getId());
        userRegistered.setName(user.getName());
        userRegistered.setUsername(user.getUsername());
        userRegistered.setRole(user.getRole().getName());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userRegistered.setJwt(jwt);

        return userRegistered;

    }

}
