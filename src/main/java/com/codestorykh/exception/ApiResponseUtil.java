package com.codestorykh.exception;

import java.time.LocalDateTime;

public class ApiResponseUtil {

    public static ApiResponseEntityDto createApiResponseEntityDto(
            String errorCode, int statusCode, String message, String errorDescription,
            Object responseData) {
        return ApiResponseEntityDto.builder()
                .errorCode(errorCode)
                .statusCode(statusCode)
                .message(message)
                .messageDescription(errorDescription)
                .timestamp(LocalDateTime.now())
                .responseData(responseData)
                .build();
    }

    public static ApiResponseEntityDto successResponse(Object object) {
        return ApiResponseUtil
                .createApiResponseEntityDto(
                        "200",
                         200,
                        "Success",
                        "Success",
                        object);

    }

}
