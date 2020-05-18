package pl.kw.app.account.boundary.primary.dto;

public class PeselException
        extends RuntimeException {
    public PeselException(String errorMessage) {
        super(errorMessage);
    }
}
