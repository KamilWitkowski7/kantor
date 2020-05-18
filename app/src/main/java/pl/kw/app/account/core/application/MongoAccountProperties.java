package pl.kw.app.account.core.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "spring.data.mongodb.account")
@Getter
@Setter
public class MongoAccountProperties {
    private String uri;
}
