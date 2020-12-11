package com.qyc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qyc
 * @time 2020/11/16 - 19:31
 */
@Data
public class InsVo implements Serializable{
    private String  insName;
    private String insPhoneNumber;
    private String insClass;
}
