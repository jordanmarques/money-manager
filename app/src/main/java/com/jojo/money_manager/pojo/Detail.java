package com.jojo.money_manager.pojo;

import java.math.BigDecimal;

public class Detail {
    private String tag;
    private BigDecimal count;

    public Detail() {
    }

    public Detail(String tag, BigDecimal count) {
        this.tag = tag;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public BigDecimal getCount() {
        return count;
    }
    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
