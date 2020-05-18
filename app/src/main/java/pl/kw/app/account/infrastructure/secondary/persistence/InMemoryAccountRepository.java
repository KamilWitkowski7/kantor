package pl.kw.app.account.infrastructure.secondary.persistence;

import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections.map.LRUMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import pl.kw.app.account.boundary.secondary.AccountRepository;
import pl.kw.app.account.core.application.Account;

@ConditionalOnProperty(value = "mongodb.replace.in-memory", havingValue = "true")
@Component
public class InMemoryAccountRepository implements AccountRepository { // Adapter
    private static final int STORE_SIZE = 200;
    private final Map<String, Account> accountStore = new LRUMap(STORE_SIZE);

    @Override
    public String saveAccount(Account account) {
        synchronized (this) {
            accountStore.put(account.getPesel(), account);
        }
        return account.getPesel();
    }

    @Override
    public String updateAccount(Account account) {
        synchronized (this) {
            accountStore.put(account.getPesel(), account);
        }
        return account.getPesel();
    }

    @Override
    public synchronized Optional<Account> getAccountById(String id) {
        return Optional.ofNullable(accountStore.get(id));
    }
}
