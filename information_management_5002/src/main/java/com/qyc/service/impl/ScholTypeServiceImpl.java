package com.qyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.type.ScholarshipType;
import com.qyc.mapper.ScholarshipTypeMapper;
import org.springframework.stereotype.Service;

/**
 * @author qyc
 * @time 2020/11/26 - 23:40
 */
@Service
public class ScholTypeServiceImpl extends ServiceImpl<ScholarshipTypeMapper, ScholarshipType> {
    public String selectTypeNameById(String schTypeId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",schTypeId);
        return baseMapper.selectOne(wrapper).getSchType();
    }
}
