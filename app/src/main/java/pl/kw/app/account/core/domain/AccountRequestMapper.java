package pl.kw.app.account.core.domain;

import pl.kw.app.account.boundary.CreateAccountCommand;
import pl.kw.app.account.boundary.primary.dto.Currencies;

import java.util.Map;

class AccountRequestMapper {
    public static Account projectRequest(CreateAccountCommand request, int minimalAgeWhenCreatedAccount) {
        return Account
                .builder()
                .currencies(Map.ofEntries(Map.entry(Currencies.PLN, request.getAccountToCreate().getBalanceInPln())))
                .firstName(request.getAccountToCreate().getFirstName())
                .lastName(request.getAccountToCreate().getLastName())
                .pesel(request.getAccountToCreate().getPesel())
                .minimalAgeWhenCreatedAccount(minimalAgeWhenCreatedAccount)
                .build();
    }
}
