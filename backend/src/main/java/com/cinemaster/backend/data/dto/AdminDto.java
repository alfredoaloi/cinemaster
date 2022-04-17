package com.cinemaster.backend.data.dto;


public class AdminDto extends AccountDto {

    private String type = "ADMIN";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
