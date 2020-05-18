package pl.kw.app.account.infrastructure.primary.web;

import static io.restassured.RestAssured.with;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import pl.kw.app.account.boundary.primary.dto.Currencies;
import pl.kw.app.account.core.application.Account;

import java.math.BigDecimal;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = {AccountControllerTest.TestPropertiesInitializer.class})
class AccountControllerTest {
    private static final String ACCOUNT_URI = "/accounts/";
    private static final String EXCHANGE_URI = "/accounts/exchange";
    private static final String PESEL = "53052657997";

    @LocalServerPort
    private Integer port;

    static class TestPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            final TestPropertyValues values = TestPropertyValues.of(
                    "mongodb.replace.in-memory=true",
                    "account.minimal-age-when-created-account=18"
            );
            values.applyTo(configurableApplicationContext);
        }
    }


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(BIG_DECIMAL));
    }

    @Test
    void givenValidRequestCreateAccountSuccessfully() {
        saveAccount()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(PESEL));
    }

    @Test
    void getAccountThatWasSaved() {
        Account account = getAccount();

        Map<Currencies, BigDecimal> currencies = Map.ofEntries(Map.entry(Currencies.PLN, BigDecimal.valueOf(1000.5)));
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(PESEL)
                .isEqualTo(account.getPesel());
        softAssertions.assertThat(18)
                .isEqualByComparingTo(account.getMinimalAgeWhenCreatedAccount());
        softAssertions.assertThat("Kamil")
                .isEqualTo(account.getFirstName());
        softAssertions.assertThat("Witkowski")
                .isEqualTo(account.getLastName());
        account.getCurrencies().forEach((currency, value) -> {
            softAssertions.assertThat(currencies.get(currency))
                    .isEqualByComparingTo(value);
        });

        softAssertions.assertAll();
    }

    private ValidatableResponse saveAccount() {
        return with()
                .body(this.getClass().getResourceAsStream("/account/requests/createAccount.json"))
                .log().body()
                .when()
                .request()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(ACCOUNT_URI)
                .then()
                .log()
                .body();
    }

    private Account getAccount() {
        return with()
                .when()
                .request()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(ACCOUNT_URI + PESEL)
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Account.class);
    }
}
