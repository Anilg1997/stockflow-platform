package com.stockflow.common.constants;

public final class ServiceConstants {

    private ServiceConstants() {}

    // ── Kafka Topics ──────────────────────────────────────────
    public static final String TOPIC_USER_CREATED = "user.created";
    public static final String TOPIC_USER_UPDATED = "user.updated";
    public static final String TOPIC_ORDER_CREATED = "order.created";
    public static final String TOPIC_ORDER_EXECUTED = "order.executed";
    public static final String TOPIC_ORDER_CANCELLED = "order.cancelled";
    public static final String TOPIC_TRADE_EXECUTED = "trade.executed";
    public static final String TOPIC_FUND_DEPOSITED = "fund.deposited";
    public static final String TOPIC_FUND_WITHDRAWN = "fund.withdrawn";
    public static final String TOPIC_HOLDINGS_UPDATED = "holdings.updated";
    public static final String TOPIC_PRICE_UPDATED = "price.updated";
    public static final String TOPIC_ALERT_TRIGGERED = "alert.triggered";
    public static final String TOPIC_NOTIFICATION_SEND = "notification.send";
    public static final String TOPIC_AUDIT_EVENT = "audit.event";
    public static final String TOPIC_SIP_EXECUTED = "sip.executed";

    // ── Order Status ──────────────────────────────────────────
    public static final String ORDER_OPEN = "OPEN";
    public static final String ORDER_PARTIALLY_EXECUTED = "PARTIALLY_EXECUTED";
    public static final String ORDER_EXECUTED = "EXECUTED";
    public static final String ORDER_CANCELLED = "CANCELLED";
    public static final String ORDER_REJECTED = "REJECTED";

    // ── Order Sides ───────────────────────────────────────────
    public static final String SIDE_BUY = "BUY";
    public static final String SIDE_SELL = "SELL";

    // ── Order Types ───────────────────────────────────────────
    public static final String ORDER_TYPE_MARKET = "MARKET";
    public static final String ORDER_TYPE_LIMIT = "LIMIT";
    public static final String ORDER_TYPE_SL = "SL";
    public static final String ORDER_TYPE_SLM = "SL-M";

    // ── Trade Types ───────────────────────────────────────────
    public static final String TRADE_INTRADAY = "INTRADAY";
    public static final String TRADE_DELIVERY = "DELIVERY";

    // ── KYC Status ────────────────────────────────────────────
    public static final String KYC_PENDING = "PENDING";
    public static final String KYC_VERIFIED = "VERIFIED";
    public static final String KYC_REJECTED = "REJECTED";

    // ── Alert Conditions ──────────────────────────────────────
    public static final String ALERT_ABOVE = "ABOVE";
    public static final String ALERT_BELOW = "BELOW";

    // ── IPO Status ────────────────────────────────────────────
    public static final String IPO_UPCOMING = "UPCOMING";
    public static final String IPO_OPEN = "OPEN";
    public static final String IPO_CLOSED = "CLOSED";
    public static final String IPO_LISTED = "LISTED";

    // ── SIP Status ────────────────────────────────────────────
    public static final String SIP_ACTIVE = "ACTIVE";
    public static final String SIP_PAUSED = "PAUSED";
    public static final String SIP_COMPLETED = "COMPLETED";

    // ── HTTP Headers ──────────────────────────────────────────
    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_CORRELATION_ID = "X-Correlation-Id";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
}
