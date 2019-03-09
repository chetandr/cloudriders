package com.cloudrider.semicolon.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> requestHeader(boolean isJsonHeader) {
        //TODO: Put following keys in Constant.java
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        if (!isJsonHeader) {
            headers.put("Content-Type", "application/x-www-form-urlencoded");
        } else {
            headers.put("Content-Type", "application/json");
        }
        return headers;
    }
}
