package com.codestorykh.service.handler;

import com.codestorykh.constant.Constant;
import com.codestorykh.dto.PaymentRequest;
import com.codestorykh.enumeration.PaymentMethod;
import com.codestorykh.enumeration.PaymentStatus;
import com.codestorykh.model.Payment;
import com.codestorykh.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@Slf4j
public class PaymentHandlerService {

    private final PaymentRepository paymentRepository;
    private final KhQRHandlerService khQRHandlerService;
    private final CashHandlerService cashHandlerService;

    public PaymentHandlerService(PaymentRepository paymentRepository,
                                 KhQRHandlerService khQRHandlerService,
                                 CashHandlerService cashHandlerService) {
        this.paymentRepository = paymentRepository;
        this.khQRHandlerService = khQRHandlerService;
        this.cashHandlerService = cashHandlerService;
    }

    public String postingToPaymentGateway(PaymentRequest paymentRequest) {
        log.info("Posting to payment gateway");

        if(Constant.BANK.equalsIgnoreCase(paymentRequest.getPaymentMethod())) {
            log.info("Payment method is bank");
            String khQRServerResponse = khQRHandlerService.postingToKhQRApi(paymentRequest);
            savePaymentTransaction(paymentRequest, khQRServerResponse);

            // verify the response make sure it success or fail
            if(StringUtils.hasText(khQRServerResponse)){
                return Constant.SUCCESS;
            }
            return Constant.FAILED;
        }

        if(Constant.CASH.equalsIgnoreCase(paymentRequest.getPaymentMethod())) {
            log.info("Payment method is cash");
            // call cash api
            String cashServerResponse = cashHandlerService.postingToCashApi(paymentRequest);

            savePaymentTransaction(paymentRequest, cashServerResponse);

            // verify the response make sure it success or fail
            if(StringUtils.hasText(cashServerResponse)){
                return Constant.SUCCESS;
            }
            return Constant.FAILED;
        }

        if(Constant.CARD.equalsIgnoreCase(paymentRequest.getPaymentMethod())) {
            log.info("Payment method is card");
            return Constant.SUCCESS;
        }

        log.info("Payment method is not supported");
        return Constant.FAILED;
    }

    public void savePaymentTransaction(PaymentRequest paymentRequest, String response) {

        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()));
        if(StringUtils.hasText(response)) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }
        payment.setAmount(paymentRequest.getAmount());
        payment.setCreatedAt(new Date());
        payment.setCreatedBy(Constant.SYSTEM);

        log.info("Saving payment transaction: {}", payment);
        paymentRepository.save(payment);
    }

}
