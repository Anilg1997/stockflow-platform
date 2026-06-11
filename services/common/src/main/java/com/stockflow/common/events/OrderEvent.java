package com.stockflow.common.events;

import com.stockflow.common.dto.OrderDTO;

public class OrderEvent extends BaseEvent {

    private OrderAction action;
    private OrderDTO order;

    public enum OrderAction {
        CREATED, PARTIALLY_EXECUTED, EXECUTED, CANCELLED, REJECTED
    }

    public OrderEvent() {}

    public OrderEvent(OrderAction action, OrderDTO order) {
        this.action = action;
        this.order = order;
    }

    public OrderAction getAction() { return action; }
    public void setAction(OrderAction action) { this.action = action; }

    public OrderDTO getOrder() { return order; }
    public void setOrder(OrderDTO order) { this.order = order; }
}
