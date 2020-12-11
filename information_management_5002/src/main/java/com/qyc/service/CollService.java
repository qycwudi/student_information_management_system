package com.qyc.service;

import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.qyc.entity.CascaderParent;
import com.qyc.entity.InsInfoVO;
import com.qyc.entity.InsVo;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/18 - 16:05
 */
public interface CollService {
    //辅导员 分页+模糊查询name、phone、classNum
    Map<Object,Object> selectPage(int num, int size, InsVo insVo);

    //添加辅导员
    boolean insertColl(CollegeAdmin collegeAdmin);

    boolean updateColl(CollegeAdmin collegeAdmin);

    boolean deleteColl(String id);

    CollegeAdmin getCollInfo(Long id);

    CollegeAdmin selectAdminInfo(String id);

    CollegeAdmin selectCollInfo(String id);

    boolean updateCollById(CollegeAdmin collegeAdmin);
}
