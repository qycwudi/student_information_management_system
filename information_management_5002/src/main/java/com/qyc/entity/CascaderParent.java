package com.qyc.entity;

import lombok.Data;

import java.util.List;

/**
 * @author qyc
 * @time 2020/11/17 - 8:53
 */
@Data
public class CascaderParent {
    private String value;
    private String label;
    private List<CascaderChild> children;
}
