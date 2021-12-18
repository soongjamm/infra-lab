package com.soongjamm.example.batch;

import com.soongjamm.example.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MoneyCopyProcessor implements ItemProcessor<Account, Account> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyCopyProcessor.class);

    @Override
    public Account process(final Account account) {
        if (!account.getNationality().equals("Korea")) {
            return null;
        }
        int before = account.getAmount();
        account.easyMoney();
        int after = account.getAmount();

        LOGGER.info("돈 복사::Account Number={} ----> ( {} ) => ( {} )", account.getAccountNumber(), before, after);

        return account;
    }
}