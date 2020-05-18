package pl.kw.app.account.infrastructure.primary.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.kw.app.account.boundary.CreateAccountCommand;
import pl.kw.app.account.boundary.ExchangeMoneyInAccountCommand;
import pl.kw.app.account.boundary.GetAccountCommand;
import pl.kw.app.account.boundary.primary.dto.PeselValue;
import pl.kw.app.account.boundary.secondary.dto.Currency;
import pl.kw.app.account.core.application.Account;
import pl.kw.app.account.core.application.ExchangeMoneyInAccountCommandHandler;
import pl.kw.app.account.core.application.GetAccountCommandHandler;
import pl.kw.app.account.core.domain.AgeException;
import pl.kw.app.account.core.application.ConditionalOnPropertyNotEmpty;
import pl.kw.app.account.core.application.CreateAccountCommandHandler;
import pl.kw.app.account.core.domain.AlreadyExistException;
import pl.kw.app.account.infrastructure.secondary.currency.NbpJsonIntegration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/accounts/")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ConditionalOnPropertyNotEmpty("mongodb.replace.in-memory")
@Validated
class AccountController {
    private CreateAccountCommandHandler createAccountCommandHandler;
    private GetAccountCommandHandler getAccountCommandHandler;
    private ExchangeMoneyInAccountCommandHandler exchangeMoneyInAccountCommandHandler;

    @ApiOperation(value = "Create new account")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Invalid parameters provided", response = void.class),
            @ApiResponse(code = 409, message = "Trying to create a duplicate", response = void.class),
            @ApiResponse(code = 500, message = "Technical error", response = void.class)
    })
    public ResponseEntity<?> createAccount(
            @Valid
            @NotNull
            @ApiParam(value = "Account to create", required = true)
            @RequestBody CreateAccountCommand command
    ) throws AgeException {
        log.info("App got request [{}]", command);
        try {
            return new ResponseEntity<>(createAccountCommandHandler.handle(command), HttpStatus.OK);
        } catch (AgeException ageException) {
            return new ResponseEntity<>(ageException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AlreadyExistException alreadyExistException) {
            return new ResponseEntity<>(alreadyExistException.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception exception) {
            log.error("Exception occured: ", exception);
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get account by id")
    @GetMapping(value = "/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Account.class),
            @ApiResponse(code = 400, message = "Invalid parameters provided", response = void.class),
            @ApiResponse(code = 500, message = "Technical error", response = void.class)
    })
    public ResponseEntity<?> getAccount(
            @Valid
            @NotNull
            @PeselValue
            @ApiParam(value = "Id of account to get", required = true)
            @PathVariable String id
    )  {
        log.info("App got request [{}]", id);
        try {
            return new ResponseEntity<>(getAccountCommandHandler.handle(GetAccountCommand.of(id)), HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Exception occured: ", exception);
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Exchange currency in account")
    @PostMapping(value = "/exchange")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Account.class),
            @ApiResponse(code = 400, message = "Invalid parameters provided", response = void.class),
            @ApiResponse(code = 500, message = "Technical error", response = void.class)
    })
    public ResponseEntity<?> exchange(
            @Valid
            @NotNull
            @ApiParam(value = "Exchange details", required = true)
            @RequestBody ExchangeMoneyInAccountCommand command
    )  {
        log.info("App got request [{}]", command);
        return exchangeMoneyInAccountCommandHandler.handle(command);
    }
}
