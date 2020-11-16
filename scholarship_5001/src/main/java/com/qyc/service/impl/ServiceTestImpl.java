package com.qyc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.StudentMessage;
import com.qyc.Main5001;
import com.qyc.entity.StudentMessageVO;
import com.qyc.mapper.MapperTest;
import com.qyc.service.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qyc
 * @time 2020/11/15 - 13:33
 */
@Service
public class ServiceTestImpl extends ServiceImpl<MapperTest, StudentMessage> implements ServiceTest {
    @Override
    public void insert(StudentMessage studentMessage) {
//        StudentMessage studentMessage = new StudentMessage();
//        BeanUtil.copyProperties(studentMessageVO,studentMessage);
//        System.out.println(studentMessage);
//        studentMessage.setStuName(studentMessageVO.getStuName());
//        studentMessage.setStuSex(studentMessageVO.getStuSex());
//        studentMessage.setStuPhoneNumber(studentMessageVO.getStuPhoneNumber());
//        System.out.println(studentMessage);
        baseMapper.insert(studentMessage);
    }

    @Override
    public StudentMessageVO show() {
        StudentMessage studentMessage =  baseMapper.showStu();
        StudentMessageVO studentMessageVO = new StudentMessageVO();
        BeanUtil.copyProperties(studentMessage,studentMessageVO);
        return studentMessageVO;
    }
}
