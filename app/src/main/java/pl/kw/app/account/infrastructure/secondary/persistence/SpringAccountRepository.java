package pl.kw.app.account.infrastructure.secondary.persistence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;

import pl.kw.app.account.core.application.Account;
import pl.kw.app.account.core.application.ConditionalOnPropertyNotEmpty;

@ConditionalOnProperty(value = "mongodb.replace.in-memory", havingValue = "false", matchIfMissing = true)
@ConditionalOnPropertyNotEmpty("spring.data.mongodb.account.uri")
public interface SpringAccountRepository extends MongoRepository<Account, String> {
}
