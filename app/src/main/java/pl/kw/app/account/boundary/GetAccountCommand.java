package pl.kw.app.account.boundary;

import pl.kw.app.account.boundary.primary.dto.PeselValue;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value(staticConstructor = "of")
public class GetAccountCommand {
    @Valid
    @NotEmpty(message = "{pesel.notEmpty}")
    @PeselValue
    @ApiModelProperty(example = "53052657997")
    private final String pesel;
}
