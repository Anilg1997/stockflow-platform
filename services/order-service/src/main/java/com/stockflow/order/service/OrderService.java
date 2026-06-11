package com.stockflow.order.service;

import com.stockflow.order.dto.PlaceOrderRequest;
import com.stockflow.order.model.Order;
import com.stockflow.order.repository.OrderRepository;
import com.stockflow.common.constants.ServiceConstants;
import com.stockflow.common.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Transactional
    public Order placeOrder(UUID userId, PlaceOrderRequest req) {
        Order order = new Order();
        order.setUserId(userId);
        order.setSymbol(req.getSymbol().toUpperCase());
        order.setOrderType(req.getOrderType());
        order.setSide(req.getSide());
        order.setTradeType(req.getTradeType());
        order.setQuantity(req.getQuantity());
        order.setPrice(req.getPrice());
        order.setTriggerPrice(req.getTriggerPrice());
        order.setStatus(ServiceConstants.ORDER_OPEN);
        return orderRepo.save(order);
    }

    @Transactional
    public Order cancelOrder(UUID userId, UUID orderId) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (!order.getUserId().equals(userId))
            throw new IllegalArgumentException("Access denied");
        if (!order.getStatus().equals(ServiceConstants.ORDER_OPEN))
            throw new IllegalArgumentException("Only open orders can be cancelled");
        order.setStatus(ServiceConstants.ORDER_CANCELLED);
        order.setCancelledAt(Instant.now());
        return orderRepo.save(order);
    }

    public Order getOrder(UUID userId, UUID orderId) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (!order.getUserId().equals(userId))
            throw new IllegalArgumentException("Access denied");
        return order;
    }

    public List<Order> getOrderHistory(UUID userId) {
        return orderRepo.findByUserIdOrderByPlacedAtDesc(userId);
    }

    public Page<Order> getOrderHistoryPaged(UUID userId, Pageable pageable) {
        return orderRepo.findByUserId(userId, pageable);
    }

    public List<Order> getOrdersBySymbol(UUID userId, String symbol) {
        return orderRepo.findByUserIdAndSymbol(userId, symbol.toUpperCase());
    }
}
