package com.qyc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.ClassInfo;
import com.qyc.entity.ClassSelectVO;
import com.qyc.entity.Info_classVo;
import com.qyc.entity.InsVo;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/16 - 19:49
 */
public interface ClassService {
    Long selectInsIdByClassName(String className);

    //根据classname查找所属辅导员id
    Long selectInsIdByClassId(String className);

    Integer selectClassNumByInsId(Long id);
    List<ClassInfo> selectClassListByInstId(Long id);
    //查找年级列表
    List<ClassInfo> selectList();

    List<ClassInfo> selectListByClassGrade(String classGrade);
    boolean insertColl(ClassInfo info);

    boolean deleteClass(String id);

    boolean updateClass(ClassInfo instructor);

    ClassInfo selectClassInfo(Long id);

    Map<String,Object> selectPage(int num, int size, ClassSelectVO insVo);

    List<Info_classVo> selectIns();

    String selectClassNameByClassId(Long stuClassId);

    String selectClassIdByClassName(String bj);
}
