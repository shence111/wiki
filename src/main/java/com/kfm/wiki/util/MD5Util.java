package com.kfm.wiki.util;

import org.apache.commons.codec.digest.Md5Crypt;

import java.nio.charset.StandardCharsets;

public class MD5Util {
    private static final String MD5_SALT = "kfamiao4";

    public static String encode(String str){
        return encode(str, MD5_SALT);

    }

    public static String encode(String str, String salt){
        return Md5Crypt.md5Crypt(str.getBytes(StandardCharsets.UTF_8), salt, "");
    }

    public static String encode(String str, int count){
        String temp = str;
        for (int i = 0; i < count; i ++){
            temp = encode(temp);
        }
        return temp;
    }
}
