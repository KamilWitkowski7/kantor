package pl.kw.app.account.core.domain;

import pl.kw.app.account.core.application.properties.AccountProperties;

public class AccountDomainConfiguration {
    private static Properties createProperties(AccountProperties accountProperties) {
        return Properties
                .builder()
                .minimalAgeWhenCreatedAccount(accountProperties.getMinimalAgeWhenCreatedAccount())
                .minimalAgeNotMet(accountProperties.getMinimalAgeNotMet())
                .build();
    }

    public static AccountCreator
        createAccountCalculation(AccountProperties accountProperties) {
        Properties properties = createProperties(accountProperties);
        return new AccountCreator(
                properties
        );
    }
}
