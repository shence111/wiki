package com.kfm.wiki.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R implements Serializable {
    private Integer code;
    private String message;
    private Object data;
    private Long time;

    public static R ok(){
        return new R(200, "请求成功", null, System.currentTimeMillis());
    }

    public static R ok(Object date){
        return new R(200, "请求成功", date, System.currentTimeMillis());
    }

    public static R failed(String message){
        return new R(500, message, null, System.currentTimeMillis());
    }
}
