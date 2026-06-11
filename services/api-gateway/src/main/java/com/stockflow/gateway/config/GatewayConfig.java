package com.stockflow.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${app.services.auth-service:lb://auth-service}")
    private String authServiceUri;

    @Value("${app.services.user-service:lb://user-service}")
    private String userServiceUri;

    @Value("${app.services.market-data-service:lb://market-data-service}")
    private String marketDataServiceUri;

    @Value("${app.services.order-service:lb://order-service}")
    private String orderServiceUri;

    @Value("${app.services.holdings-service:lb://holdings-service}")
    private String holdingsServiceUri;

    @Value("${app.services.funds-service:lb://funds-service}")
    private String fundsServiceUri;

    @Value("${app.services.watchlist-service:lb://watchlist-service}")
    private String watchlistServiceUri;

    @Value("${app.services.mf-service:lb://mf-service}")
    private String mfServiceUri;

    @Value("${app.services.ipo-service:lb://ipo-service}")
    private String ipoServiceUri;

    @Value("${app.services.news-service:lb://news-service}")
    private String newsServiceUri;

    @Value("${app.services.analytics-service:lb://analytics-service}")
    private String analyticsServiceUri;

    @Value("${app.services.report-service:lb://report-service}")
    private String reportServiceUri;

    @Value("${app.services.alert-service:lb://alert-service}")
    private String alertServiceUri;

    @Value("${app.services.search-service:lb://search-service}")
    private String searchServiceUri;

    @Value("${app.services.trade-execution-service:lb://trade-execution-service}")
    private String tradeExecutionUri;

    @Value("${app.services.brokerage-service:lb://brokerage-service}")
    private String brokerageUri;

    @Value("${app.services.notification-service:lb://notification-service}")
    private String notificationUri;

    @Value("${app.services.audit-service:lb://audit-service}")
    private String auditServiceUri;

    @Value("${app.services.sip-service:lb://sip-service}")
    private String sipServiceUri;

    @Value("${app.services.mf-holdings-service:lb://mf-holdings-service}")
    private String mfHoldingsServiceUri;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // ── Auth Service ─────────────────────────────────
            .route("auth-service", r -> r
                .path("/api/auth/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .circuitBreaker(cb -> cb
                        .setName("authServiceCB")
                        .setFallbackUri("forward:/fallback/auth")))
                .uri(authServiceUri))

            // ── User Service ─────────────────────────────────
            .route("user-service", r -> r
                .path("/api/users/**")
                .filters(f -> f.stripPrefix(1))
                .uri(userServiceUri))

            // ── Market Data Service ──────────────────────────
            .route("market-data-service", r -> r
                .path("/api/market/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .circuitBreaker(cb -> cb
                        .setName("marketDataCB")
                        .setFallbackUri("forward:/fallback/market")))
                .uri(marketDataServiceUri))

            // ── Order Service ────────────────────────────────
            .route("order-service", r -> r
                .path("/api/orders/**")
                .filters(f -> f.stripPrefix(1))
                .uri(orderServiceUri))

            // ── Holdings Service ─────────────────────────────
            .route("holdings-service", r -> r
                .path("/api/holdings/**")
                .filters(f -> f.stripPrefix(1))
                .uri(holdingsServiceUri))

            // ── Funds Service ────────────────────────────────
            .route("funds-service", r -> r
                .path("/api/funds/**")
                .filters(f -> f.stripPrefix(1))
                .uri(fundsServiceUri))

            // ── Watchlist Service ────────────────────────────
            .route("watchlist-service", r -> r
                .path("/api/watchlist/**")
                .filters(f -> f.stripPrefix(1))
                .uri(watchlistServiceUri))

            // ── Mutual Fund Service ──────────────────────────
            .route("mf-service", r -> r
                .path("/api/mf/**")
                .filters(f -> f.stripPrefix(1))
                .uri(mfServiceUri))

            // ── IPO Service ──────────────────────────────────
            .route("ipo-service", r -> r
                .path("/api/ipo/**")
                .filters(f -> f.stripPrefix(1))
                .uri(ipoServiceUri))

            // ── News Service ─────────────────────────────────
            .route("news-service", r -> r
                .path("/api/news/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .circuitBreaker(cb -> cb
                        .setName("newsServiceCB")
                        .setFallbackUri("forward:/fallback/news")))
                .uri(newsServiceUri))

            // ── Analytics Service ────────────────────────────
            .route("analytics-service", r -> r
                .path("/api/analytics/**")
                .filters(f -> f.stripPrefix(1))
                .uri(analyticsServiceUri))

            // ── Report Service ───────────────────────────────
            .route("report-service", r -> r
                .path("/api/reports/**")
                .filters(f -> f.stripPrefix(1))
                .uri(reportServiceUri))

            // ── Alert Service ────────────────────────────────
            .route("alert-service", r -> r
                .path("/api/alerts/**")
                .filters(f -> f.stripPrefix(1))
                .uri(alertServiceUri))

            // ── Search Service ───────────────────────────────
            .route("search-service", r -> r
                .path("/api/search/**")
                .filters(f -> f.stripPrefix(1))
                .uri(searchServiceUri))

            // ── Trade Execution Service ───────────────────────
            .route("trade-execution-service", r -> r
                .path("/api/trades/**")
                .filters(f -> f.stripPrefix(1))
                .uri(tradeExecutionUri))

            // ── Brokerage Service ─────────────────────────────
            .route("brokerage-service", r -> r
                .path("/api/brokerage/**")
                .filters(f -> f.stripPrefix(1))
                .uri(brokerageUri))

            // ── Notification Service ──────────────────────────
            .route("notification-service", r -> r
                .path("/api/notifications/**")
                .filters(f -> f.stripPrefix(1))
                .uri(notificationUri))

            // ── Audit Service ─────────────────────────────────
            .route("audit-service", r -> r
                .path("/api/audit/**")
                .filters(f -> f.stripPrefix(1))
                .uri(auditServiceUri))

            // ── SIP Service ───────────────────────────────────
            .route("sip-service", r -> r
                .path("/api/sip/**")
                .filters(f -> f.stripPrefix(1))
                .uri(sipServiceUri))

            // ── MF Holdings Service ───────────────────────────
            .route("mf-holdings-service", r -> r
                .path("/api/mf-holdings/**")
                .filters(f -> f.stripPrefix(1))
                .uri(mfHoldingsServiceUri))

            // ── GraphQL endpoint ─────────────────────────────
            .route("graphql", r -> r
                .path("/graphql")
                .uri("lb://api-gateway"))

            // ── WebSocket for GraphQL subscriptions ──────────
            .route("graphql-ws", r -> r
                .path("/graphql-ws")
                .uri("lb:ws://api-gateway"))

            .build();
    }
}
