package pl.kw.app.account.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;

import pl.kw.app.account.boundary.primary.dto.AccountToCreate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateAccountCommand {
    @Valid
    @NotNull
    private AccountToCreate accountToCreate;
}
