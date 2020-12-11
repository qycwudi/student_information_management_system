package com.qyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.ClassInfo;
import com.bean.StudentMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qyc
 * @time 2020/11/19 - 20:33
 */
@Mapper
public interface StudentMapper extends BaseMapper<StudentMessage> {

}
