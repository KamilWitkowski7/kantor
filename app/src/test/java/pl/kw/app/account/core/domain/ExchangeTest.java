package pl.kw.app.account.core.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import pl.kw.app.account.boundary.primary.dto.Currencies;
import pl.kw.app.account.boundary.primary.dto.TransactionDetails;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

class ExchangeTest {
    @Test
    void shouldBeAbleToExchange() {
        // given
        TransactionDetails transactionDetails = TransactionDetails
                .builder()
                .initialCurrency(Currencies.PLN)
                .targetCurrency(Currencies.USD)
                .valueOfCurrencyToExchange(BigDecimal.valueOf(1000))
                .build();

        Account account = Account
                .builder()
                .firstName("Kamil")
                .lastName("Witkowski")
                .minimalAgeWhenCreatedAccount(18)
                .pesel("53052657997")
                .currencies(Map.ofEntries(Map.entry(Currencies.PLN, BigDecimal.valueOf(1000))))
                .build();

        // when
        boolean canExchange = account.canExchange(transactionDetails);
        // then
        assertThat(canExchange).isTrue();
    }
}
