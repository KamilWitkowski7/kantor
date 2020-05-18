package pl.kw.app.account.core.domain;

import pl.kw.app.account.boundary.primary.dto.Currencies;
import pl.kw.app.account.boundary.primary.dto.TransactionDetails;
import pl.kw.app.account.boundary.secondary.dto.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Builder
@Value
public class Account {
    private final String pesel;
    private final String firstName;
    private final String lastName;
    @With
    private final Map<Currencies, BigDecimal> currencies;
    private final int minimalAgeWhenCreatedAccount;

    boolean isValid() {
        return PeselValidator.hasCorrectAge(this.pesel, this.minimalAgeWhenCreatedAccount);
    }

    boolean canExchange(TransactionDetails transactionDetails) {
        BigDecimal currentValueInCurrency = currencies.get(transactionDetails.getInitialCurrency());
        if (currentValueInCurrency == null) {
            currentValueInCurrency = BigDecimal.ZERO;
        }
        return currentValueInCurrency.compareTo(transactionDetails.getValueOfCurrencyToExchange()) > -1;
    }

    Account exchange(TransactionDetails transactionDetails, Rate rate) {
        if (canExchange(transactionDetails)) {
            BigDecimal currentValueInCurrencyFrom = currencies.get(transactionDetails.getInitialCurrency());
            BigDecimal currentValueInCurrencyTo = currencies.get(transactionDetails.getTargetCurrency());
            BigDecimal valueToExchange = transactionDetails.getValueOfCurrencyToExchange();

            if (currentValueInCurrencyTo == null) {
                currentValueInCurrencyTo = BigDecimal.ZERO;
            }

            if (transactionDetails.getInitialCurrency().equals(Currencies.PLN)) {
                return this.withCurrencies(Map.ofEntries(
                        Map.entry(transactionDetails.getInitialCurrency(), currentValueInCurrencyFrom.subtract(valueToExchange)),
                        Map.entry(transactionDetails.getTargetCurrency(),
                                currentValueInCurrencyTo.add(valueToExchange.divide(rate.getAsk(), 2, RoundingMode.HALF_DOWN)))
                ));
            } else {
                return this.withCurrencies(Map.ofEntries(
                        Map.entry(transactionDetails.getInitialCurrency(), currentValueInCurrencyFrom.subtract(valueToExchange)),
                        Map.entry(
                                transactionDetails.getTargetCurrency(),
                                currentValueInCurrencyTo.add(
                                        valueToExchange.multiply(rate.getBid())
                                ).setScale(2, RoundingMode.HALF_DOWN)
                        )
                ));
            }
        }
        return this;
    }
}
