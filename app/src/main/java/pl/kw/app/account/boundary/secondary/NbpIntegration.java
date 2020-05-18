package pl.kw.app.account.boundary.secondary;

import org.springframework.http.ResponseEntity;

public interface NbpIntegration { // Port
    ResponseEntity<?> getCurrency();
}

