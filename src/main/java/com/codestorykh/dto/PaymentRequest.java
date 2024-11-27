package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequest {

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_description")
    private String paymentDescription;
}
