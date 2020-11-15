package com.service.impl;

import com.bean.StudentMessage;
import com.mapper.StudentMessageMapper;
import com.service.StudentMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-15
 */
@Service
public class StudentMessageServiceImpl extends ServiceImpl<StudentMessageMapper, StudentMessage> implements StudentMessageService {

}
