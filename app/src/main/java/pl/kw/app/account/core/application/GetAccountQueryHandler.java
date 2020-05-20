package pl.kw.app.account.core.application;

import org.springframework.stereotype.Component;

import pl.kw.app.account.boundary.GetAccountQuery;
import pl.kw.app.account.boundary.secondary.AccountRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAccountQueryHandler {
    private final AccountRepository accountRepository;

    public Optional<Account> handle(GetAccountQuery command) {
        return accountRepository.getAccountById(command.getPesel());
    }
}
