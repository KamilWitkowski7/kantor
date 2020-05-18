package pl.kw.app.account.boundary.secondary.dto;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Rate {
    @JsonProperty("no")
    private final String no;
    @JsonProperty("effectiveDate")
    private final LocalDate effectiveDate;
    @JsonProperty("bid")
    private final BigDecimal bid;
    @JsonProperty("ask")
    private final BigDecimal ask;

    @JsonCreator(mode = PROPERTIES)
    Rate(
            @JsonProperty("no") @NotNull String no,
            @JsonProperty("effectiveDate") @NotNull LocalDate effectiveDate,
            @JsonProperty("bid") @NotNull BigDecimal bid,
            @JsonProperty("ask") @NotNull BigDecimal ask
    ) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.bid = bid;
        this.ask = ask;
    }
}
