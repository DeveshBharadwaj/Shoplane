package com.cfs.Ecomm.dto;

public class PaymentRequestDto {

    private double amount;

    private PaymentRequestDto(){}

    public PaymentRequestDto(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
