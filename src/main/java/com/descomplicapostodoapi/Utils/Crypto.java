package com.descomplicapostodoapi.Utils;

import org.springframework.util.DigestUtils;

public class Crypto {
    public static String MD5(String value) {
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }
}
