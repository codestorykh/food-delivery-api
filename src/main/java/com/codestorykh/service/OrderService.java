package com.codestorykh.service;

import com.codestorykh.dto.OrderRequest;
import com.codestorykh.dto.OrderResponse;
import com.codestorykh.dto.OrderStatusRequest;

public interface OrderService {

    OrderResponse create(OrderRequest orderRequest);

    OrderResponse update(OrderStatusRequest orderStatusRequest);
}
