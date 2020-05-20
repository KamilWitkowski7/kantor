package pl.kw.app.account.boundary.primary.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class TransactionDetails {
    @ApiModelProperty(example = "PLN")
    @NotNull(message = "{initialCurrency.notEmpty}")
    private final Currencies initialCurrency;

    @ApiModelProperty(example = "USD")
    @NotNull(message = "{targetCurrency.notEmpty}")
    private final Currencies targetCurrency;

    @NotNull(message = "{valueOfCurrencyToExchange.notNull}")
    @Min(value = 0, message = "{valueOfCurrencyToExchange.min}")
    private final BigDecimal valueOfCurrencyToExchange;
}
