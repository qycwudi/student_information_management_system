package com.qyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.StudentMessage;
import com.bean.state.StudentMessageState;
import com.qyc.mapper.StudentMessageStateMapper;
import com.qyc.service.StudentMessageStateService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author qyc
 * @time 2020/11/20 - 18:56
 */
@Service
public class StudentMessageStateServiceImpl extends ServiceImpl<StudentMessageStateMapper, StudentMessageState> implements StudentMessageStateService {

    @Override
    @Cacheable(cacheNames = "getStateById")
    public String getStateById(String stuStateInfoId) {
        StudentMessageState studentMessage = new StudentMessageState();
        studentMessage = baseMapper.selectById(new Long(stuStateInfoId));
        if(studentMessage!=null){
            return studentMessage.getState();
        }
        return "";
    }
}
