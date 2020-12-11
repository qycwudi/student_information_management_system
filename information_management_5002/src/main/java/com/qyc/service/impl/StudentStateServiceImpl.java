package com.qyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.type.StudentState;
import com.qyc.mapper.StudentStateMapper;
import com.qyc.service.StudentStateService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author qyc
 * @time 2020/11/20 - 18:56
 */
@Service
public class StudentStateServiceImpl extends ServiceImpl<StudentStateMapper, StudentState> implements StudentStateService {
    @Override
    @Cacheable(cacheNames = "getStateByID")
    public String getStateByID(String stuStateId) {
        StudentState studentState = new StudentState();
        studentState = baseMapper.selectById(new Long(stuStateId));
        if(studentState!=null){
            return studentState.getState();
        }
        return "";
    }
}
