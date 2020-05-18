package pl.kw.app.account.core.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.kw.app.account.boundary.secondary.AccountRepository;
import pl.kw.app.account.boundary.secondary.NbpIntegration;
import pl.kw.app.account.core.application.properties.AccountProperties;
import pl.kw.app.account.core.domain.AccountDomainConfiguration;
import pl.kw.app.account.core.domain.AccountTransactionHandler;

@Configuration
@ConditionalOnPropertyNotEmpty("mongodb.replace.in-memory")
public class AccountConfiguration {
    @Bean
    public CreateAccountCommandHandler createAccountCommandHandler(
            AccountRepository accountRepository,
            AccountProperties accountProperties
    ) {
        return new CreateAccountCommandHandler(
                AccountDomainConfiguration.createAccountCalculation(accountProperties),
                accountRepository,
                accountProperties
        );
    }

    @Bean
    public ExchangeMoneyInAccountCommandHandler createExchangeMoneyInAccountCommandHandler(
            AccountRepository accountRepository,
            NbpIntegration nbpIntegration
    ) {
        return new ExchangeMoneyInAccountCommandHandler(
                accountRepository,
                new AccountTransactionHandler(),
                nbpIntegration
        );
    }
}
