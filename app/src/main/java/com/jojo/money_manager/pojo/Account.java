package com.jojo.money_manager.pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.realm.RealmObject;


public class Account extends RealmObject{
    private Long balance;

    public Account(){}

    public Account(Long balance) {
        this.balance = new Long(balance);
    }

    public void credit(Long value){
        balance = balance + value;
    }

    public void debit(Long value){
        balance = balance - value;
    }

    public Long getBalance() {
        return balance;
    }
    public void setBalance(Long balance) {
        this.balance = balance ;
    }
}
