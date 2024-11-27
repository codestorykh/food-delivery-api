package com.codestorykh.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseEntityDto {

    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("message_description")
    private String messageDescription;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("response_data")
    private Object responseData;
}
