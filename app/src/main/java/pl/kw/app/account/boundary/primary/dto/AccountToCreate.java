package pl.kw.app.account.boundary.primary.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AccountToCreate {
    @NotEmpty(message = "{firstName.notEmpty}")
    @ApiModelProperty(example = "Kamil")
    private final String firstName;

    @NotEmpty(message = "{lastName.notEmpty}")
    @ApiModelProperty(example = "Witkowski")
    private final String lastName;

    @NotEmpty(message = "{pesel.notEmpty}")
    @PeselValue
    @ApiModelProperty(example = "53052657997")
    private final String pesel;

    @NotNull(message = "{balanceInPln.notNull}")
    @Min(value = 0, message = "{balanceInPln.min}")
    @ApiModelProperty(example = "1000.50")
    private final BigDecimal balanceInPln;
}
