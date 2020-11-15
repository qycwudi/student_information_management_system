package com.qyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.StudentMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qyc
 * @time 2020/11/15 - 14:08
 */
@Mapper
public interface MapperTest extends BaseMapper<StudentMessage> {
    int insertStu(StudentMessage studentMessage);
    StudentMessage showStu();
}
