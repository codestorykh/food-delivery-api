package com.codestorykh.service;

import com.codestorykh.dto.PaymentRequest;

public interface PaymentService {

    String pay(PaymentRequest paymentRequest);

    String inquiry(String orderId);
}
