package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DeliveryPartnerRequest {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String username;
    private String password;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("dob")
    private String dateOfBirth;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("vehicle")
    private String vehicle;

    @JsonProperty("available")
    private boolean available;
}
