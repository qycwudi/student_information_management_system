package com.qyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.ClassInfo;
import com.bean.Instructor;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qyc
 * @time 2020/11/16 - 19:46
 */
@Mapper
public interface ClassMapper extends BaseMapper<ClassInfo> {
}
