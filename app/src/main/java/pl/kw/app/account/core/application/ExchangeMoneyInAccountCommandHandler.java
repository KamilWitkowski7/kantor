package pl.kw.app.account.core.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pl.kw.app.account.boundary.ExchangeMoneyInAccountCommand;
import pl.kw.app.account.boundary.secondary.AccountRepository;
import pl.kw.app.account.boundary.secondary.NbpIntegration;
import pl.kw.app.account.boundary.secondary.dto.Currency;
import pl.kw.app.account.core.domain.AccountTransactionHandler;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeMoneyInAccountCommandHandler {
    private final AccountRepository accountRepository;
    private final AccountTransactionHandler accountTransactionHandler;
    private final NbpIntegration nbpIntegration;

    public ResponseEntity<?> handle(ExchangeMoneyInAccountCommand command) {
        Optional<Account> account = accountRepository.getAccountById(command.getPesel());
        if (account.isPresent()) {
            if (accountTransactionHandler.canExchange(
                    command.getTransactionDetails(),
                    AccountMapper.projectToDomainModel(account.get()))
            ) {
                ResponseEntity<?> responseEntity = nbpIntegration.getCurrency();
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    return responseEntity;
                }
                Currency currency = (Currency) responseEntity.getBody();
                ResponseEntity<?> updatedAccount =
                        new ResponseEntity<>(accountTransactionHandler.makeExchange(
                                command.getTransactionDetails(),
                                AccountMapper.projectToDomainModel(account.get()), currency.getRates().get(0)
                        ),
                                HttpStatus.OK
                        );

                accountRepository.updateAccount(
                        AccountMapper.projectToPersistentModel((pl.kw.app.account.core.domain.Account) updatedAccount.getBody())
                );
                return updatedAccount;
            } else {
                return new ResponseEntity<>("brak tylu środków", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("brak konta", HttpStatus.BAD_REQUEST);
        }
    }
}
