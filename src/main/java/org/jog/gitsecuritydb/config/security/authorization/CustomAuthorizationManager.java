package org.jog.gitsecuritydb.config.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.exception.ObjectNotFoundException;
import org.jog.gitsecuritydb.persistence.entity.security.GrantedPermission;
import org.jog.gitsecuritydb.persistence.entity.security.Operation;
import org.jog.gitsecuritydb.persistence.entity.security.User;
import org.jog.gitsecuritydb.persistence.repository.security.OperationRepository;
import org.jog.gitsecuritydb.service.UserService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final OperationRepository operationRepository;
    private final UserService userService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {

        HttpServletRequest request = requestContext.getRequest();

        String url = extractUrl(request);
        String httpMethod = request.getMethod();

        boolean isPublic =isPublic(url,httpMethod);
        if(isPublic){
            return new AuthorizationDecision(true);
        }

        boolean isGranted = isGranted(url,httpMethod, authentication.get());

        return new AuthorizationDecision(isGranted);

    }

    private String extractUrl(HttpServletRequest request) {

        String contextPath = request.getContextPath();
        String url = request.getRequestURI();

        return url.replace(contextPath, "");
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessPoint = operationRepository.findPublicAccess();

        return publicAccessPoint
                .stream()
                .anyMatch(getOperationPredicate(url, httpMethod));
    }

    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> {

            String basePath = operation.getModule().getBasePath();

            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));

            Matcher matcher = pattern.matcher(url);

            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication) {

        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }

        List<Operation> operations = obtainOperation(authentication);

        return operations
                .stream()
                .anyMatch(getOperationPredicate(url, httpMethod));
    }

    private List<Operation> obtainOperation(Authentication authentication) {

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;


        String username = (String) authToken.getPrincipal();

        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        return user.getRole()
                .getPermissions()
                .stream()
                .map(GrantedPermission::getOperation)
                .collect(Collectors.toList());


    }
}
