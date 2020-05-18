package pl.kw.app.account.core.domain;

public class AlreadyExistException
        extends RuntimeException {
    public AlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
