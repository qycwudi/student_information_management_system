package com.qyc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author qyc
 * @time 2020/11/15 - 13:28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentMessageVO {
    private String id;
    private String stuName;
    private String stuSex;
    private String stuPhoneNumber;
    private String account;
    private String stuNation;
    private String stuMajor;
    private String stuClassName;
    private String stuPoliticsStatus;
    private String stuIdCard;
    private String stuDuty;
    private String stuInsName;
    private String stuState;
    private String stuStateInfo;
    private String imgUrl;
    private String stuPassword;
}
