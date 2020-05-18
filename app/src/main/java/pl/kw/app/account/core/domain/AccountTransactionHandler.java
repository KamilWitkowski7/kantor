package pl.kw.app.account.core.domain;

import pl.kw.app.account.boundary.primary.dto.TransactionDetails;
import pl.kw.app.account.boundary.secondary.dto.Rate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountTransactionHandler {
    public boolean canExchange(TransactionDetails transactionDetails, Account account) {
        return account.canExchange(transactionDetails);
    }

    public Account makeExchange(TransactionDetails transactionDetails, Account account, Rate rate) throws AgeException {
        return account.exchange(transactionDetails, rate);
    }
}
