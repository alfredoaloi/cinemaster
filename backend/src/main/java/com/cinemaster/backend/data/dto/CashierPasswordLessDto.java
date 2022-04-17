package com.cinemaster.backend.data.dto;

public class CashierPasswordLessDto extends AccountPasswordLessDto {

    private String type = "CASHIER";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
