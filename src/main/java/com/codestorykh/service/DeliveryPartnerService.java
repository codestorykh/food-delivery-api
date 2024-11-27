package com.codestorykh.service;

import com.codestorykh.dto.DeliveryPartnerRequest;
import com.codestorykh.dto.DeliveryPartnerResponse;

import java.util.List;

public interface DeliveryPartnerService {

    DeliveryPartnerResponse create(DeliveryPartnerRequest deliveryPartnerRequest);
    DeliveryPartnerResponse update(Long id, DeliveryPartnerRequest deliveryPartnerRequest);
    void delete(Long id);
    DeliveryPartnerResponse getById(Long id);
    List<DeliveryPartnerResponse> getAll();
}
