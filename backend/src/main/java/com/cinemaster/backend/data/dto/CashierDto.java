package com.cinemaster.backend.data.dto;


public class CashierDto extends AccountDto {

    private String type = "CASHIER";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
