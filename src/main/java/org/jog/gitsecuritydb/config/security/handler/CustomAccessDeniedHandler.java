package org.jog.gitsecuritydb.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();

        apiErrorDTO.setBackendMessage(accessDeniedException.getLocalizedMessage());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setTimeStamp(LocalDateTime.now());
        apiErrorDTO.setMessage("AccessDenied you don't have permissions");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String apiErrorAsJson = objectMapper.writeValueAsString(apiErrorDTO);

        response.getWriter().write(apiErrorAsJson);
    }
}
