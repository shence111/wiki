package com.kfm.wiki.util;

import java.util.UUID;

public class UUIDUtil {
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
