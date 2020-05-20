package pl.kw.app.account.core.application;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import pl.kw.app.account.core.application.properties.AccountProperties;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {
    private final AccountProperties accountProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(accountProperties.getNbpConnectionTimeoutInMilliSeconds()))
                .setReadTimeout(Duration.ofMillis(accountProperties.getNbpReadTimeoutInMilliSeconds()))
                .build();
    }
}
