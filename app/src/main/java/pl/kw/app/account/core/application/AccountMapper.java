package pl.kw.app.account.core.application;

import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static Account projectToPersistentModel(pl.kw.app.account.core.domain.Account account) {
        return Account
                .builder()
                .currencies(account.getCurrencies())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .minimalAgeWhenCreatedAccount(account.getMinimalAgeWhenCreatedAccount())
                .pesel(account.getPesel())
                .build();
    }

    public static pl.kw.app.account.core.domain.Account projectToDomainModel(Account account) {
        return pl.kw.app.account.core.domain.Account
                .builder()
                .currencies(account.getCurrencies())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .minimalAgeWhenCreatedAccount(account.getMinimalAgeWhenCreatedAccount())
                .pesel(account.getPesel())
                .build();
    }
}
