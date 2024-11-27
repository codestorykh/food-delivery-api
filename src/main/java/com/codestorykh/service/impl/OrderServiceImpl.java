package com.codestorykh.service.impl;

import com.codestorykh.dto.OrderRequest;
import com.codestorykh.dto.OrderResponse;
import com.codestorykh.dto.OrderStatusRequest;
import com.codestorykh.enumeration.DeliveryStatus;
import com.codestorykh.enumeration.OrderStatus;
import com.codestorykh.enumeration.PaymentMethod;
import com.codestorykh.enumeration.PaymentStatus;
import com.codestorykh.exception.BadRequestErrorException;
import com.codestorykh.exception.InternalServerErrorException;
import com.codestorykh.exception.UserNotFoundErrorException;
import com.codestorykh.model.*;
import com.codestorykh.repository.*;
import com.codestorykh.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Transactional
    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        Optional<User> user = userRepository.findById(orderRequest.getUserId());
        if(user.isEmpty()) {
            log.warn("User with id {} not found", orderRequest.getUserId());
            throw new UserNotFoundErrorException("User not found.");
        }

        Optional<Order> verifyOrderPending = orderRepository.findFirstByUserIdAndOrderStatusNot(user.get().getId(),
                OrderStatus.DELIVERED);
        if(verifyOrderPending.isPresent()) {
            log.warn("User with id {} has pending order", orderRequest.getUserId());
            throw new BadRequestErrorException("User has pending order.");
        }

        Optional<Restaurant> restaurant = restaurantRepository.findById(orderRequest.getRestaurantId());
        if(restaurant.isEmpty()) {
            log.warn("Restaurant with id {} not found", orderRequest.getRestaurantId());
            throw new InternalServerErrorException("Restaurant not found.");
        }

        List<DeliveryPartner> deliveryPartners = deliveryPartnerRepository.findAllByAvailable(true);
        if(deliveryPartners.isEmpty()) {
            log.warn("No available delivery partner found");
            throw new InternalServerErrorException("No available delivery partner found.");
        }

        if(orderRequest.getOrderItemRequests().isEmpty()) {
            log.info("Order items are empty");
            throw new BadRequestErrorException("Order items required non-null.");
        }

        final var assignToDeliveryPartner = deliveryPartners.getFirst();
        final var orderId = user.get().getPhoneNumber()+UUID.randomUUID();


        Payment payment = new Payment();
        payment.setAmount(orderRequest.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentRequest().getPaymentMethod()));
        payment.setDescription(orderRequest.getPaymentRequest().getPaymentDescription());
        payment.setCreatedBy(user.get().getUsername());
        payment.setCreatedAt(new Date());

        log.info("Payment created: {}", payment);
        paymentRepository.saveAndFlush(payment);
        if(payment.getId() == null) {
            log.error("Payment not created");
            throw new InternalServerErrorException("Request failed please try again later.");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(new Date());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setTax(orderRequest.getTax());
        order.setDeliveryFee(orderRequest.getDeliveryFee());
        order.setUserId(user.get().getId());
        order.setDeliveryPartnerId(assignToDeliveryPartner.getId());
        order.setRestaurantId(restaurant.get().getId());
        order.setPaymentId(payment.getId());

        log.info("Order created: {}", order);
        orderRepository.saveAndFlush(order);

        if(order.getId() == null) {
            log.error("Order not created");
            throw new InternalServerErrorException("Request failed please try again later.");
        }

        Delivery delivery = new Delivery();
        delivery.setDeliveryPartnerId(assignToDeliveryPartner.getId());
        delivery.setOrderId(order.getId());
        delivery.setPickupAddress(restaurant.get().getAddress());
        delivery.setDeliveryAddress(user.get().getAddress());
        delivery.setDeliveryPartnerId(assignToDeliveryPartner.getId());
        delivery.setDeliveryFee(orderRequest.getDeliveryFee());

        log.info("Delivery created: {}", delivery);
        deliveryRepository.saveAndFlush(delivery);

        return OrderResponse.builder()
                .orderId(orderId)
                .paymentMethod(payment.getPaymentMethod().name())
                .totalAmount(orderRequest.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .build();
    }

    @Transactional
    @Override
    public OrderResponse update(OrderStatusRequest orderStatusRequest) {

        Optional<Order> order = orderRepository.findFirstByOrderId(orderStatusRequest.getOrderId());
        if(order.isEmpty()) {
            log.warn("Order with id {} not found", orderStatusRequest.getOrderId());
            throw new InternalServerErrorException("Order not found.");
        }

        Optional<Delivery> delivery = deliveryRepository.findFirstByOrderId(order.get().getId());
        if(delivery.isEmpty()) {
            log.warn("Delivery with order id {} not found", order.get().getId());
            throw new InternalServerErrorException("Delivery not found.");
        }

        Optional<Payment> payment = paymentRepository.findById(order.get().getPaymentId());
        if(payment.isEmpty()) {
            log.warn("Payment with id {} not found", order.get().getPaymentId());
            throw new InternalServerErrorException("Payment not found.");
        }

        if(OrderStatus.PENDING.name().equals(orderStatusRequest.getOrderStatus())){
            order.get().setOrderStatus(OrderStatus.PREPARING);
        }
        if(OrderStatus.PREPARING.name().equals(orderStatusRequest.getOrderStatus())){
            order.get().setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);

            //
            delivery.get().setPickupTime(new Date());
            delivery.get().setDeliveryStatus(DeliveryStatus.PICKED_UP);
            deliveryRepository.saveAndFlush(delivery.get());
        }
        if(OrderStatus.OUT_FOR_DELIVERY.name().equals(orderStatusRequest.getOrderStatus())){
            order.get().setOrderStatus(OrderStatus.DELIVERED);
            payment.get().setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.saveAndFlush(payment.get());

            //
            delivery.get().setDeliveryTime(new Date());
            delivery.get().setDeliveryStatus(DeliveryStatus.DELIVERED);
            deliveryRepository.saveAndFlush(delivery.get());
        }

        orderRepository.saveAndFlush(order.get());

        return OrderResponse.builder()
                .orderId(order.get().getOrderId())
                .orderStatus(order.get().getOrderStatus().name())
                .totalAmount(order.get().getTotalAmount())
                .paymentMethod(payment.get().getPaymentMethod().name())
                .build();
    }
}
