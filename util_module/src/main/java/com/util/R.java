package com.util;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/7/1 - 17:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class R {
    private boolean success;
    private int code;
    private String message;
    private Map<String,Object> data;
    //成功静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("成功");
        r.data = new HashMap<>();
        return r;
    }
    //失败
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage("失败");
        return r;
    }
    public R data(String key, Object value){
        this.data.put(key,value);
        return this;
    }
}
