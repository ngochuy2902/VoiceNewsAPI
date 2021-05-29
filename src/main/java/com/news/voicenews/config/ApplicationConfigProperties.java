package com.news.voicenews.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@NoArgsConstructor
@Getter
@Setter
public class ApplicationConfigProperties {

    private Jwt jwt;
    private Cors cors;

    @Getter
    @Setter
    public static class Jwt {
        private String secretKey;
        private String issuer;
        private Long expiration;
    }

    @Getter
    @Setter
    public static class Cors {
        private String allowedOrigin;
    }
}
