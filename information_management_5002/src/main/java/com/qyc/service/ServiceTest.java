package com.qyc.service;

import com.bean.StudentMessage;
import com.qyc.entity.StudentMessageVO;

/**
 * @author qyc
 * @time 2020/11/15 - 13:32
 */
public interface ServiceTest {
    public void insert(StudentMessage studentMessage);
    public StudentMessageVO show();
}
