package com.qyc.entity;

import com.bean.Instructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qyc
 * @time 2020/11/17 - 11:48
 */
@Data
public class InsInfoVO extends Instructor implements Serializable {
    private List<String> classList;
}
