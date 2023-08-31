package com.sneakermarket.domain.post;

public enum SaleStatus {
    판매중("판매중"),
    예약중("예약중"),
    판매완료("판매완료");

    private final String value;

    SaleStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}