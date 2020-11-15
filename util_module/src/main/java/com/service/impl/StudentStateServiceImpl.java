package com.service.impl;

import com.bean.type.StudentState;
import com.mapper.StudentStateMapper;
import com.service.StudentStateService;
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
public class StudentStateServiceImpl extends ServiceImpl<StudentStateMapper, StudentState> implements StudentStateService {

}
