package pl.kw.app.account.infrastructure.secondary.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kw.app.account.boundary.secondary.AccountRepository;
import pl.kw.app.account.core.application.Account;
import pl.kw.app.account.core.application.ConditionalOnPropertyNotEmpty;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(value = "mongodb.replace.in-memory", havingValue = "false", matchIfMissing = true)
@ConditionalOnPropertyNotEmpty("spring.data.mongodb.account.uri")
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoAccountRepository implements AccountRepository { // Adapter
    private final SpringAccountRepository repository;

    @Override
    @Transactional
    public String saveAccount(Account account) {
        return repository.save(account).getPesel();
    }

    @Override
    @Transactional
    public String updateAccount(Account account) {
        return repository.save(account).getPesel();
    }

    @Override
    public Optional<Account> getAccountById(String id) {
        return repository.findById(id);
    }
}
