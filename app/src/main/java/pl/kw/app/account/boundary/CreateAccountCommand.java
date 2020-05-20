package pl.kw.app.account.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;

import pl.kw.app.account.boundary.primary.dto.AccountToCreate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateAccountCommand {
    @Valid
    @NotNull
    private final AccountToCreate accountToCreate;
}
