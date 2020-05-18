package pl.kw.app.account.core.application;

import pl.kw.app.account.boundary.CreateAccountCommand;
import pl.kw.app.account.boundary.secondary.AccountRepository;
import pl.kw.app.account.boundary.secondary.dto.Id;
import pl.kw.app.account.core.application.properties.AccountProperties;
import pl.kw.app.account.core.domain.AgeException;
import pl.kw.app.account.core.domain.AccountCreator;
import pl.kw.app.account.core.domain.AlreadyExistException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAccountCommandHandler {
    private final AccountCreator accountCreator;
    private final AccountRepository accountRepository;
    private final AccountProperties accountProperties;

    public Id handle(CreateAccountCommand command) throws AgeException, AlreadyExistException {
        if (accountRepository.getAccountById(command.getAccountToCreate().getPesel()).isEmpty()) {
            return Id.of(accountRepository.saveAccount(AccountMapper.projectToPersistentModel(accountCreator.create(command))));
        } else {
            throw new AlreadyExistException(accountProperties.getAccountAlreadyExists());
        }
    }
}
