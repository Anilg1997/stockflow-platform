package com.stockflow.order.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.order.dto.PlaceOrderRequest;
import com.stockflow.order.model.Order;
import com.stockflow.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder(
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody PlaceOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Order placed", orderService.placeOrder(userId, request)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrder(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID orderId) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.getOrder(userId, orderId)));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Order>> cancelOrder(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID orderId) {
        return ResponseEntity.ok(ApiResponse.ok("Order cancelled", orderService.cancelOrder(userId, orderId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getOrderHistory(
            @RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.getOrderHistory(userId)));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<Order>>> getOrderHistoryPaged(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.getOrderHistoryPaged(userId, PageRequest.of(page, size))));
    }
}
