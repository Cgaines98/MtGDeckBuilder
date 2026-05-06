package com.example.config;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final List<String> audiences;

    public AudienceValidator(String audience) {
        this(Collections.singletonList(Objects.requireNonNull(audience)));
    }

    public AudienceValidator(List<String> audiences) {
        this.audiences = Objects.requireNonNull(audiences);
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        List<String> jwtAudiences = jwt.getAudience();
        if (jwtAudiences == null || jwtAudiences.isEmpty()) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "The 'aud' claim is missing", null));
        }

        for (String aud : audiences) {
            if (jwtAudiences.contains(aud)) {
                return OAuth2TokenValidatorResult.success();
            }
        }
        
        String errorMessage = String.format("The required audience is missing. Expected one of: %s. Found: %s", audiences, jwtAudiences);
        return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", errorMessage, null));
    }
}
