package com.qyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.Instructor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author qyc
 * @time 2020/11/16 - 19:39
 */
@Mapper
public interface InsMapper extends BaseMapper<Instructor> {
}
