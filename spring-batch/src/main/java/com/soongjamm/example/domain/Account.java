package com.soongjamm.example.domain;

import com.soongjamm.example.batch.AccountNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private AccountNumber accountNumber;
    private String nationality;
    private int amount;

    public void easyMoney() {
        amount *= 2;
    }
}
