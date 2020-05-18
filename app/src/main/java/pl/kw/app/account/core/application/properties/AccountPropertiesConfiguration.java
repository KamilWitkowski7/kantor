package pl.kw.app.account.core.application.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AccountProperties.class})
public class AccountPropertiesConfiguration {
}
