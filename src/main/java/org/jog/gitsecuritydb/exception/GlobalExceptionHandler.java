package org.jog.gitsecuritydb.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.jog.gitsecuritydb.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> genericExceptionHandler(Exception exception, HttpServletRequest request) {

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMessage("internal server error, please try later");
        apiErrorDTO.setTimeStamp(LocalDateTime.now());


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMessage("search error");
        apiErrorDTO.setTimeStamp(LocalDateTime.now());


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handlerAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMessage("You don't have permissions");
        apiErrorDTO.setTimeStamp(LocalDateTime.now());


        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorDTO);
    }
}
