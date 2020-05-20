package pl.kw.app.account.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;

import pl.kw.app.account.boundary.primary.dto.TransactionDetails;
import pl.kw.app.account.boundary.primary.dto.PeselValue;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class ExchangeMoneyInAccountCommand {
    @Valid
    @NotNull
    private final TransactionDetails transactionDetails;

    @NotEmpty(message = "{pesel.notEmpty}")
    @PeselValue
    @ApiModelProperty(example = "53052657997")
    private final String pesel;
}
