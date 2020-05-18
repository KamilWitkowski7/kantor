package pl.kw.app.account.core.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "account")
@Getter
@Setter
public class AccountProperties {
    private int minimalAgeWhenCreatedAccount;
    private String minimalAgeNotMet;
    private String accountAlreadyExists;
    private String nbpUri;
}
