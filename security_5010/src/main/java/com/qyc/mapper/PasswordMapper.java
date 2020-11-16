package com.qyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.bean.StudentMessage;
import com.qyc.bean.BkStaff;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import javax.annotation.ManagedBean;
import java.util.Collection;

/**
 * @author qyc
 * @time 2020/11/15 - 18:50
 */
@Mapper
public interface PasswordMapper extends BaseMapper<BkStaff> {
    String getcoll(String account);
    String getins(String account);
    String getstu(String account);

    Instructor getIns(String account);
    CollegeAdmin getColl(String account);
    StudentMessage getStu(String account);

}
