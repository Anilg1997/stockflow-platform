package com.stockflow.gateway.config;

import com.stockflow.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/auth")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiResponse<Void> authFallback() {
        return ApiResponse.error("Auth service is temporarily unavailable. Please try again later.");
    }

    @GetMapping("/market")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiResponse<Void> marketFallback() {
        return ApiResponse.error("Market data service is temporarily unavailable. Please try again later.");
    }

    @GetMapping("/news")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiResponse<Void> newsFallback() {
        return ApiResponse.error("News service is temporarily unavailable. Please try again later.");
    }
}
