package com.codestorykh.service.handler;

import com.codestorykh.constant.Constant;
import com.codestorykh.dto.DeliveryPartnerRequest;
import com.codestorykh.dto.DeliveryPartnerResponse;
import com.codestorykh.enumeration.VehicleType;
import com.codestorykh.model.DeliveryPartner;
import com.codestorykh.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class DeliveryPartnerHandlerService {

    public DeliveryPartner convertDeliveryPartnerRequestToDeliveryPartner(DeliveryPartner deliveryPartner,
            DeliveryPartnerRequest deliveryPartnerRequest) {

        deliveryPartner.setFirstName(deliveryPartnerRequest.getFirstName());
        deliveryPartner.setLastName(deliveryPartnerRequest.getLastName());
        deliveryPartner.setUsername(deliveryPartnerRequest.getUsername());
        deliveryPartner.setPassword(deliveryPartnerRequest.getPassword());
        deliveryPartner.setGender(deliveryPartnerRequest.getGender());
        deliveryPartner.setDateOfBirth(DateTimeUtils
                .convertStringToDate(deliveryPartnerRequest.getDateOfBirth()));
        deliveryPartner.setPhoneNumber(deliveryPartnerRequest.getPhoneNumber());
        deliveryPartner.setEmail(deliveryPartnerRequest.getEmail());
        deliveryPartner.setAddress(deliveryPartnerRequest.getAddress());
        deliveryPartner.setVehicleType(VehicleType.valueOf(deliveryPartnerRequest.getVehicle()));
        deliveryPartner.setAvailable(deliveryPartnerRequest.isAvailable());
        if(deliveryPartner.getId() == null) {
            deliveryPartner.setCreatedAt(new Date());
            deliveryPartner.setCreatedBy(Constant.SYSTEM);
        }

        return deliveryPartner;
    }

    public DeliveryPartnerResponse convertDeliveryPartnerToDeliveryPartnerResponse(
            DeliveryPartner deliveryPartner) {

        DeliveryPartnerResponse deliveryPartnerResponse = new DeliveryPartnerResponse();
        deliveryPartnerResponse.setId(deliveryPartner.getId());
        deliveryPartnerResponse.setFirstName(deliveryPartner.getFirstName());
        deliveryPartnerResponse.setLastName(deliveryPartner.getLastName());
        deliveryPartnerResponse.setUsername(deliveryPartner.getUsername());
        deliveryPartnerResponse.setGender(deliveryPartner.getGender());
        deliveryPartnerResponse.setDateOfBirth(deliveryPartner.getDateOfBirth().toString());
        deliveryPartnerResponse.setPhoneNumber(deliveryPartner.getPhoneNumber());
        deliveryPartnerResponse.setEmail(deliveryPartner.getEmail());
        deliveryPartnerResponse.setAddress(deliveryPartner.getAddress());
        deliveryPartnerResponse.setVehicle(deliveryPartner.getVehicleType().toString());
        deliveryPartnerResponse.setAvailable(deliveryPartner.isAvailable());
        deliveryPartnerResponse.setCreatedBy(deliveryPartner.getCreatedBy());
        deliveryPartnerResponse.setCreatedAt(deliveryPartner.getCreatedAt().toString());
        deliveryPartnerResponse.setUpdatedBy(deliveryPartner.getUpdatedBy());
        if(deliveryPartner.getUpdatedAt() != null)
            deliveryPartnerResponse.setUpdatedAt(deliveryPartner.getUpdatedAt().toString());

        return deliveryPartnerResponse;
    }
}
