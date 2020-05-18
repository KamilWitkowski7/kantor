package pl.kw.app.account.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class Properties {
    private int minimalAgeWhenCreatedAccount;
    private String minimalAgeNotMet;
}
