package com.qyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.ClassInfo;
import com.bean.CollegeAdmin;
import com.qyc.entity.ClassInfoVo;
import com.qyc.entity.ClassSelectVO;
import com.qyc.entity.Info_classVo;
import com.qyc.entity.InsVo;
import com.qyc.mapper.ClassMapper;
import com.qyc.service.ClassService;
import com.qyc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sun.font.TrueTypeFont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/16 - 19:49
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, ClassInfo> implements ClassService {
    @Autowired
    private InsServiceImpl insService;
    @Override
    public List<ClassInfo> selectClassListByInstId(Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("class_ins_id",id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean insertColl(ClassInfo info) {
        if(info.getClassGrade()!=""&&info.getClassName()!=null){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("class_name",info.getClassName());
            if(baseMapper.selectOne(queryWrapper)==null){
                info.setClassStuSum(0);
                info.setClassInsId(0L);
                baseMapper.insert(info);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteClass(String id) {
        Long ID = new Long(id);
        int i = baseMapper.deleteById(ID);
        if(i==1){
            return true;
        }
        return false;
    }

    @Autowired
    private StudentServiceImpl studentService;
    @Override
    public boolean updateClass(ClassInfo classInfo) {
        if(classInfo.getClassGrade()!=""&&classInfo.getClassName()!=null&&classInfo.getClassStuSum()!=null){
            int i = baseMapper.updateById(classInfo);
            Long classId = classInfo.getClassInsId();
            Long insId = classInfo.getClassInsId();
            new Thread(()->{
                studentService.updateInsIdByClassId(classId,insId);
            }).start();
            if(i ==1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public ClassInfo selectClassInfo(Long id) {

        return baseMapper.selectById(id);
    }


    @Override
    public Map<String,Object> selectPage(int num, int size, ClassSelectVO insVo) {
        Page<ClassInfo> page = new Page<>(num,size);
        QueryWrapper wrapper = new QueryWrapper();
        if(insVo!=null){
            if(insVo.getClassName()!=null){
                wrapper.like("class_name",insVo.getClassName());
            }
            if(insVo.getClassGrade()!=null){
                wrapper.like("class_grade",insVo.getClassGrade());
            }
            if(insVo.getInsID()!=null){
                wrapper.eq("class_ins_id",insVo.getInsID());
            }
        }
        baseMapper.selectPage(page,wrapper);
        List<ClassInfoVo> list = new ArrayList<>();
        for (ClassInfo c :
                page.getRecords()) {
            ClassInfoVo classInfoVo = new ClassInfoVo();
            classInfoVo.setId(c.getId());
            classInfoVo.setClassName(c.getClassName());
            classInfoVo.setClassGrade(c.getClassGrade());
            classInfoVo.setClassStuSum(c.getClassStuSum());
            String insName = insService.selectInsNameByID(c.getClassInsId());
            classInfoVo.setClassInsName(insName);
            list.add(classInfoVo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("pageList",list);
        map.put("size",page.getSize());
        map.put("total",page.getTotal());
        map.put("num",page.getCurrent());
        return map;
    }

    //查找所有辅导员姓名+id
    @Override
    public List<Info_classVo> selectIns() {
        List<Info_classVo> list = insService.selectInsName();
        return list;
    }


    @Override
    public List<ClassInfo> selectList() {
        QueryWrapper w = new QueryWrapper();
        w.select("class_grade");
        w.groupBy("class_grade");
        w.orderByDesc("class_grade");
        return baseMapper.selectList(w);
    }

    @Override
    @Cacheable(cacheNames = "selectClassNameByClassId")
    public String selectClassNameByClassId(Long stuClassId) {
        ClassInfo classInfo = baseMapper.selectById(stuClassId);
        if(classInfo!=null){
            String name = baseMapper.selectById(stuClassId).getClassName();
            return name;
        }else return "班级不存在";
    }

    @Override
    public String selectClassIdByClassName(String bj) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("class_name",bj);
        String Id = baseMapper.selectOne(wrapper).getId().toString();
        return Id;
    }

    @Override
    public List<ClassInfo> selectListByClassGrade(String classGrade) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("class_grade",classGrade);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Long selectInsIdByClassName(String className) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("class_name",className);
        ClassInfo info = baseMapper.selectOne(wrapper);
        if(info!=null){
            return info.getClassInsId();
        }else {
            return null;
        }

    }


    @Override
    public Long selectInsIdByClassId(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",id);
        ClassInfo info = baseMapper.selectOne(wrapper);
        if(info!=null){
            return info.getClassInsId();
        }else {
            return null;
        }
    }

    @Override
    public Integer selectClassNumByInsId(Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("class_ins_id",id);
        Integer sum = baseMapper.selectCount(wrapper);
        return sum;
    }
}
