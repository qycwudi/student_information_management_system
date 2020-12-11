package com.qyc.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author qyc
 * @time 2020/11/19 - 22:30
 */
@Data
public class ClassInfoVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String className;

    private String classGrade;

    private Integer classStuSum;

    private String classInsName;
}
