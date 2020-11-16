package com.qyc.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qyc
 * @time 2020/8/25 - 10:32
 */
@Data
public class User implements Serializable {
    private String username;
    private String password;
}
