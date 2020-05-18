package pl.kw.app.account.boundary.secondary.dto;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Currency {
    @JsonProperty("table")
    private final String table;
    @JsonProperty("currency")
    private final String currency;
    @JsonProperty("code")
    private final String code;
    @JsonProperty("rates")
    private final List<Rate> rates;

    @JsonCreator(mode = PROPERTIES)
    Currency(
            @JsonProperty("table") @NotNull String table,
            @JsonProperty("currency") @NotNull String currency,
            @JsonProperty("code") @NotNull String code,
            @JsonProperty("rates") @NotNull List<Rate> rates
    ) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }
}
