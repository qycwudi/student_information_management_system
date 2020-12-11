package com.qyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.ClassInfo;
import com.bean.ScholarshipInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qyc
 * @time 2020/11/16 - 19:46
 */
@Mapper
public interface ScholMapper extends BaseMapper<ScholarshipInfo> {
}
