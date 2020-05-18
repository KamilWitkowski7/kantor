package pl.kw.app.account.infrastructure.secondary.persistence;

import static org.junit.Assert.assertNotNull;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import pl.kw.app.account.boundary.primary.dto.Currencies;
import pl.kw.app.account.boundary.secondary.AccountRepository;
import pl.kw.app.account.core.application.Account;

import java.math.BigDecimal;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = {MongoLeadIT.TestPropertiesInitializer.class})
class MongoLeadIT {
    @Container
    private static GenericContainer mongoDb;
    private static final int MONGO_PORT = 27017;
    private static final String LOCALHOST = "localhost";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "example";

    private AccountRepository accountRepository;
    private Account account;
    private Account updatedAccount;

    @Autowired
    private SpringAccountRepository springAccountRepository;

    static class TestPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            final TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.account.uri=" + "mongodb://" + USERNAME + ":" + PASSWORD
                            + "@" + LOCALHOST + ":" + mongoDb.getMappedPort(MONGO_PORT) + "/admin"
            );
            values.applyTo(configurableApplicationContext);
        }
    }

    static {
        mongoDb = new GenericContainer("mongo:4.2.2")
                .withExposedPorts(MONGO_PORT)
                .withEnv("MONGO_INITDB_ROOT_USERNAME", USERNAME)
                .withEnv("MONGO_INITDB_ROOT_PASSWORD", PASSWORD)
                .withNetworkAliases("mongo");

        mongoDb.start();
        // register JVM shutdown hook to stop the container
        Runtime.getRuntime().addShutdownHook(new Thread(mongoDb::stop));
    }

    @BeforeEach
    void setUp() {
        account = Account
                .builder()
                .pesel("53052657997")
                .minimalAgeWhenCreatedAccount(18)
                .firstName("Kamil")
                .lastName("Witkowski")
                .currencies(Map.ofEntries(
                        Map.entry(Currencies.PLN, BigDecimal.valueOf(1000)),
                        Map.entry(Currencies.USD, BigDecimal.valueOf(500))
                        )
                )
                .build();

        updatedAccount = account
                .withMinimalAgeWhenCreatedAccount(17)
                .withCurrencies(Map.ofEntries(Map.entry(Currencies.PLN, BigDecimal.valueOf(1500))))
                .withFirstName("Robert")
                .withLastName("Kazik");

        accountRepository = new MongoAccountRepository(springAccountRepository);
    }

    @Test
    void accountPersistence() {
        // Given account
        // When:
        String id = accountRepository.saveAccount(account);
        Account accountFromDb = accountRepository.getAccountById(id).orElseThrow(() -> new RuntimeException("Account not present!"));
        // Then:
        assertNotNull(id);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(accountFromDb.getFirstName()).isEqualTo(account.getFirstName());
        softAssertions.assertThat(accountFromDb.getLastName()).isEqualTo(account.getLastName());
        softAssertions.assertThat(accountFromDb.getCurrencies()).isEqualTo(account.getCurrencies());
        softAssertions.assertThat(accountFromDb.getMinimalAgeWhenCreatedAccount()).isEqualTo(account.getMinimalAgeWhenCreatedAccount());
        softAssertions.assertThat(accountFromDb.getPesel()).isEqualTo(account.getPesel());

        softAssertions.assertAll();
    }

    @Test
    void accountUpdate() {
        // Given account
        // When:
        accountRepository.saveAccount(account);

        String id = accountRepository.updateAccount(updatedAccount);

        Account accountFromDb = accountRepository.getAccountById(id).orElseThrow(() -> new RuntimeException("Account not present!"));
        // Then:
        assertNotNull(id);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(accountFromDb.getFirstName()).isEqualTo(updatedAccount.getFirstName());
        softAssertions.assertThat(accountFromDb.getLastName()).isEqualTo(updatedAccount.getLastName());
        softAssertions.assertThat(accountFromDb.getCurrencies()).isEqualTo(updatedAccount.getCurrencies());
        softAssertions.assertThat(accountFromDb.getMinimalAgeWhenCreatedAccount())
                .isEqualTo(updatedAccount.getMinimalAgeWhenCreatedAccount());
        softAssertions.assertThat(accountFromDb.getPesel()).isEqualTo(account.getPesel());

        softAssertions.assertAll();
    }
}
