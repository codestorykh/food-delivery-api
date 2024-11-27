package com.codestorykh.service.impl;

import com.codestorykh.dto.PaymentRequest;
import com.codestorykh.service.PaymentService;
import com.codestorykh.service.handler.PaymentHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentHandlerService paymentHandlerService;

    public PaymentServiceImpl(PaymentHandlerService paymentHandlerService) {
        this.paymentHandlerService = paymentHandlerService;
    }

    @Override
    public String pay(PaymentRequest paymentRequest) {

       return paymentHandlerService.postingToPaymentGateway(paymentRequest);
    }

    @Override
    public String inquiry(String orderId) {
        return "";
    }
}
