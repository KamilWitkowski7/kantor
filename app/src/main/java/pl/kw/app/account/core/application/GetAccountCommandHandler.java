package pl.kw.app.account.core.application;

import org.springframework.stereotype.Component;

import pl.kw.app.account.boundary.GetAccountCommand;
import pl.kw.app.account.boundary.secondary.AccountRepository;

import java.util.Optional;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GetAccountCommandHandler {
    private final AccountRepository accountRepository;

    public Optional<Account> handle(GetAccountCommand command) {
        return accountRepository.getAccountById(command.getPesel());
    }
}
