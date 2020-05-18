package pl.kw.app.account.core.domain;

import pl.kw.app.account.boundary.CreateAccountCommand;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountCreator {
    private final Properties properties;

    public Account create(CreateAccountCommand request) throws AgeException {
        Account account = AccountRequestMapper.projectRequest(request, properties.getMinimalAgeWhenCreatedAccount());

        if (!account.isValid()) {
            throw new AgeException(properties.getMinimalAgeNotMet());
        }
        return account;
    }
}
