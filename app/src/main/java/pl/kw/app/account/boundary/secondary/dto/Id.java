package pl.kw.app.account.boundary.secondary.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class Id {
    private final String id;
}
