package com.jojo.money_manager.pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Account {
    private BigDecimal balance;

    public Account() {
        this.balance = new BigDecimal(0);
    }

    public Account(float balance) {
        this.balance = new BigDecimal(balance);
    }

    public void credit(Float value){
        balance = balance.add(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING));
    }

    public void debit(Float value){
        balance = balance.subtract(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING));
    }

    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.CEILING);
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.CEILING);
    }
}
