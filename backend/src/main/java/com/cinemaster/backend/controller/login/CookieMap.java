package com.cinemaster.backend.controller.login;

import com.cinemaster.backend.data.dto.AccountPasswordLessDto;

import java.util.HashMap;

public class CookieMap {

    private static CookieMap instance;
    private HashMap<String, AccountPasswordLessDto> map;

    private CookieMap() {
        map = new HashMap<>();
    }

    public static CookieMap getInstance() {
        if (instance == null) {
            instance = new CookieMap();
        }
        return instance;
    }

    public HashMap<String, AccountPasswordLessDto> getMap() {
        return map;
    }
}
