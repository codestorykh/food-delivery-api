package com.codestorykh.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ApiResponseEntityDto> handleInternalServerErrorException(InternalServerErrorException ex) {
        log.error("Internal server error: {}", ex.getLocalizedMessage());

        var apiResponseEntity = ApiResponseEntityDto.builder()
                .errorCode("500")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal server error")
                .messageDescription(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(apiResponseEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundErrorException.class)
    public ResponseEntity<ApiResponseEntityDto> handleUserNotFoundErrorException(UserNotFoundErrorException ex) {
        log.error("User not found: {}", ex.getLocalizedMessage());

        ApiResponseEntityDto apiResponseEntity = ApiResponseEntityDto.builder()
                .errorCode("404")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("User not found.")
                .messageDescription(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(apiResponseEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<ApiResponseEntityDto> handleUserBadRequestErrorException(BadRequestErrorException ex) {
        log.error("Bad request exception error: {}", ex.getLocalizedMessage());

        var apiResponseEntity = ApiResponseEntityDto.builder()
                .errorCode("400")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("User not found.")
                .messageDescription(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(apiResponseEntity, HttpStatus.BAD_REQUEST);
    }
}
