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
    private String stuName;
    private String stuSex;
    private String stuPhoneNumber;
}
