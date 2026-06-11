package com.stockflow.common.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "eventType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = UserEvent.class, name = "USER_EVENT"),
    @JsonSubTypes.Type(value = OrderEvent.class, name = "ORDER_EVENT"),
    @JsonSubTypes.Type(value = TradeEvent.class, name = "TRADE_EVENT"),
    @JsonSubTypes.Type(value = PriceEvent.class, name = "PRICE_EVENT"),
    @JsonSubTypes.Type(value = FundEvent.class, name = "FUND_EVENT")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEvent {

    private String eventId;
    private Instant timestamp;
    private String source;

    public BaseEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
