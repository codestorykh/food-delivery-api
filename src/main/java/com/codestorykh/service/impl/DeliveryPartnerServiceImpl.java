package com.codestorykh.service.impl;

import com.codestorykh.constant.Constant;
import com.codestorykh.dto.DeliveryPartnerRequest;
import com.codestorykh.dto.DeliveryPartnerResponse;
import com.codestorykh.model.DeliveryPartner;
import com.codestorykh.repository.DeliveryPartnerRepository;
import com.codestorykh.service.DeliveryPartnerService;
import com.codestorykh.service.handler.DeliveryPartnerHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final DeliveryPartnerHandlerService deliveryPartnerHandlerService;


    @Override
    public DeliveryPartnerResponse create(DeliveryPartnerRequest deliveryPartnerRequest) {
        DeliveryPartner deliveryPartner = new DeliveryPartner();
        deliveryPartner = deliveryPartnerHandlerService
                .convertDeliveryPartnerRequestToDeliveryPartner(deliveryPartner, deliveryPartnerRequest);

        log.info("Save new Delivery Partner record: {}", deliveryPartner);
        deliveryPartnerRepository.saveAndFlush(deliveryPartner);

        if(deliveryPartner.getId() != null) {
            return deliveryPartnerHandlerService.convertDeliveryPartnerToDeliveryPartnerResponse(deliveryPartner);
        }

        return null;
    }

    @Override
    public DeliveryPartnerResponse update(Long id, DeliveryPartnerRequest deliveryPartnerRequest) {

        Optional<DeliveryPartner> deliveryPartner = deliveryPartnerRepository.findById(id);
        if(deliveryPartner.isEmpty()) {
            return new DeliveryPartnerResponse();
        }

        DeliveryPartner updateDeliveryPartner = deliveryPartnerHandlerService
                .convertDeliveryPartnerRequestToDeliveryPartner(deliveryPartner.get(), deliveryPartnerRequest);

        updateDeliveryPartner.setUpdatedBy(Constant.SYSTEM);
        updateDeliveryPartner.setUpdatedAt(new Date());

        deliveryPartnerRepository.saveAndFlush(updateDeliveryPartner);

        return deliveryPartnerHandlerService.convertDeliveryPartnerToDeliveryPartnerResponse(updateDeliveryPartner);
    }

    @Override
    public void delete(Long id) {
        Optional<DeliveryPartner> deliveryPartner = deliveryPartnerRepository.findById(id);
        if(deliveryPartner.isEmpty()) {
            log.info("Delivery Partner record not found with id: {}", id);
            return;
        }
        deliveryPartnerRepository.deleteById(id);
    }

    @Override
    public DeliveryPartnerResponse getById(Long id) {
        Optional<DeliveryPartner> deliveryPartner = deliveryPartnerRepository.findById(id);
        if(deliveryPartner.isEmpty()) {
            log.info("Find Delivery Partner by id {} not found.", id);
            return new DeliveryPartnerResponse();
        }
        return deliveryPartnerHandlerService
                .convertDeliveryPartnerToDeliveryPartnerResponse(deliveryPartner.get());
    }

    @Override
    public List<DeliveryPartnerResponse> getAll() {
        List<DeliveryPartner> deliveryPartners = deliveryPartnerRepository.findAll();
        if(deliveryPartners.isEmpty()) {
            log.info("Fetch all Delivery Partner not found in DB.");
            return List.of();
        }

        List<DeliveryPartnerResponse> deliveryPartnerResponses = new ArrayList<>();
        for(DeliveryPartner deliveryPartner : deliveryPartners) {

            DeliveryPartnerResponse deliveryPartnerResponse = deliveryPartnerHandlerService
                    .convertDeliveryPartnerToDeliveryPartnerResponse(deliveryPartner);

            deliveryPartnerResponses.add(deliveryPartnerResponse);
        }

        return deliveryPartnerResponses;
    }
}
