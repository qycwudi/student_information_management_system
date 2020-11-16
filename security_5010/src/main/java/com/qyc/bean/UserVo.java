package com.qyc.bean;

import lombok.Data;

/**
 * @author qyc
 * @time 2020/11/15 - 19:33
 */
@Data
public class UserVo {
    private Long ID;
    private String account;
    private String password;
    private String role;
    private String imgUrl;

}
