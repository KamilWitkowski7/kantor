package pl.kw.app.account.core.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.kw.app.account.boundary.primary.dto.PeselException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler { // https://github.com/spring-projects/spring-boot/issues/10471
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    PeselException exceptionHandler(ValidationException e) {
        return new PeselException(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    PeselException exceptionHandler(ConstraintViolationException e) {
        return new PeselException(e.getMessage());
    }
}
