package com.stockflow.auth.service;

import com.stockflow.auth.model.RefreshToken;
import com.stockflow.auth.model.User;
import com.stockflow.auth.repository.RefreshTokenRepository;
import com.stockflow.auth.repository.UserRepository;
import com.stockflow.common.dto.*;
import com.stockflow.common.events.UserEvent;
import com.stockflow.common.events.UserEvent.UserAction;
import com.stockflow.common.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

import static com.stockflow.common.constants.ServiceConstants.TOPIC_USER_CREATED;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validate uniqueness
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user = userRepository.save(user);

        // Generate tokens
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(),
            user.getUsername(), user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        saveRefreshToken(user, refreshToken);

        // Publish event
        UserDTO userDTO = toUserDTO(user);
        kafkaTemplate.send(TOPIC_USER_CREATED, new UserEvent(UserAction.CREATED, userDTO));

        return buildAuthResponse(accessToken, refreshToken, userDTO);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .or(() -> userRepository.findByEmail(request.getUsername()))
            .orElseThrow(() -> new IllegalArgumentException("Invalid username/email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid username/email or password");
        }

        if (!user.isActive()) {
            throw new IllegalStateException("Account is deactivated");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(),
            user.getUsername(), user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        saveRefreshToken(user, refreshToken);

        return buildAuthResponse(accessToken, refreshToken, toUserDTO(user));
    }

    public AuthResponse refreshToken(String refreshTokenStr) {
        UUID userId = jwtTokenProvider.getUserIdFromToken(refreshTokenStr);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String tokenHash = hashToken(refreshTokenStr);
        RefreshToken storedToken = refreshTokenRepository.findByTokenHash(tokenHash)
            .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));

        if (storedToken.isRevoked() || storedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token expired or revoked");
        }

        User user = storedToken.getUser();

        // Rotate refresh token
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getId(),
            user.getUsername(), user.getEmail());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        saveRefreshToken(user, newRefreshToken);

        return buildAuthResponse(newAccessToken, newRefreshToken, toUserDTO(user));
    }

    @Transactional
    public void logout(UUID userId, String refreshTokenStr) {
        if (refreshTokenStr != null) {
            String tokenHash = hashToken(refreshTokenStr);
            refreshTokenRepository.findByTokenHash(tokenHash)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
        }
    }

    public UserDTO getCurrentUser(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toUserDTO(user);
    }

    private void saveRefreshToken(User user, String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(hashToken(token));
        refreshToken.setExpiresAt(Instant.now().plusSeconds(
            jwtTokenProvider.getRefreshExpirationMs() / 1000));
        refreshTokenRepository.save(refreshToken);
    }

    private String hashToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(token.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private AuthResponse buildAuthResponse(String accessToken, String refreshToken, UserDTO user) {
        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(jwtExpiration);
        response.setUser(user);
        return response;
    }

    private UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setActive(user.isActive());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setFullName(user.getUsername());
        return dto;
    }
}
