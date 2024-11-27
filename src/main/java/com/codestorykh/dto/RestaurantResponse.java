package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {

    private Long id;
    private String code;
    private String name;
    private String category;
    private String description;
    private double rating;
    private String address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty
    private String openTime;

    @JsonProperty("close_time")
    private String closeTime;

    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("created_by")
    private String createdBy;
}
