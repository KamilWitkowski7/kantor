package pl.kw.app.account.core.domain;

public class AgeException
        extends RuntimeException {
    public AgeException(String errorMessage) {
        super(errorMessage);
    }
}
