package pl.kw.app.account.boundary.secondary;

import pl.kw.app.account.core.application.Account;

import java.util.Optional;

public interface AccountRepository { // Port
    String saveAccount(Account account);

    String updateAccount(Account account);

    Optional<Account> getAccountById(String id);
}

