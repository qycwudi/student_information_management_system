package com.service.impl;

import com.bean.Instructor;
import com.mapper.InstructorMapper;
import com.service.InstructorService;
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
public class InstructorServiceImpl extends ServiceImpl<InstructorMapper, Instructor> implements InstructorService {

}
