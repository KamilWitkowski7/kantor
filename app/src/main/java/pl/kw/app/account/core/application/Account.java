package pl.kw.app.account.core.application;

import org.springframework.data.annotation.Id;

import pl.kw.app.account.boundary.primary.dto.Currencies;
import java.math.BigDecimal;
import java.util.Map;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Builder
@Value
public class Account {
    @Id
    private final String pesel;
    @With
    private final String firstName;
    @With
    private final String lastName;
    @With
    private final Map<Currencies, BigDecimal> currencies;
    @With
    private final int minimalAgeWhenCreatedAccount;
}
