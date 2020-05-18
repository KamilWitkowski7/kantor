package pl.kw.app.account.boundary.primary.dto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;

class PeselValueValidatorTest {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private String[] positivePeselNumbers = new String[] {
        "90010393478"
    };
    private String[] negativePeselNumbers = new String[] {
        "1",
        "00410183892", // Pesel z 2100 roku, czyli z przyszłości
        "901301012345",
        "901135012345"
    };

    @Test
    void givenValidPeselWhenValidateThenReturnNoConstraintViolations() {
        Arrays.stream(positivePeselNumbers)
            .forEach(p -> assertTrue(isPeselAllowed(p)));
    }

    @Test
    void givenInvalidPeselNumberWhenValidateThenReturnConstraintViolations() {
        Arrays.stream(negativePeselNumbers)
            .forEach(p -> assertFalse(isPeselAllowed(p)));

    }

    private boolean isPeselAllowed(String pesel) {
        Set<ConstraintViolation<AccountToCreate>> constraintViolations = validator.validateValue(AccountToCreate.class, "pesel", pesel);
        return constraintViolations.isEmpty();
    }
}
