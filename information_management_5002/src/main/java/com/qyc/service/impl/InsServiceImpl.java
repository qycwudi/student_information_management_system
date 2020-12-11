package com.qyc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.ClassInfo;
import com.bean.Instructor;
import com.qyc.entity.*;
import com.qyc.mapper.InsMapper;
import com.qyc.service.ClassService;
import com.qyc.service.InsService;
import com.util.JiaMi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author qyc
 * @time 2020/11/16 - 19:37
 */
@Service
public class InsServiceImpl extends ServiceImpl<InsMapper,Instructor> implements InsService {

    @Autowired
    private ClassServiceImpl classServiceImpl;

    @Override
    public boolean insertIns(Instructor instructor) {
        instructor.setRole("2");
        instructor.setImgUrl("http://qyc-oostest.oss-cn-beijing.aliyuncs.com/20201027/d3fe178a27f14235bc25fbfa93eae062.png");
        if (instructor.getInsName() != "" && instructor.getInsAddress() != "" && instructor.getInsPhoneNumber().length() == 11) {

           try{
               Long phone = new Long(instructor.getInsPhoneNumber());

           }catch (NumberFormatException e){
               return false;
           }

            QueryWrapper wrapper = new QueryWrapper();
//            wrapper.eq("ins_phone_number", instructor.getInsPhoneNumber());
            wrapper.eq("account", instructor.getInsPhoneNumber());
            Instructor instructor1 = baseMapper.selectOne(wrapper);
            System.out.println("instructor1 = " + instructor1);
            if (instructor1 != null) {
                return false;
            }
            instructor.setAccount(instructor.getInsPhoneNumber());
            instructor.setInsPassword("a2abd179ebd09a63a77a7aa74981f2a4");
            int i = baseMapper.insert(instructor);
            if (i == 1) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<CascaderParent> getCascader() {
        List<CascaderParent> list = new ArrayList<>();
        List<ClassInfo> classInfos = classServiceImpl.selectList();
        for (ClassInfo claInfo:classInfos
             ) {
            CascaderParent cascaderParent = new CascaderParent();
            cascaderParent.setLabel(claInfo.getClassGrade());
            cascaderParent.setValue(claInfo.getClassGrade());
            cascaderParent.setChildren(new ArrayList<>());
            //根据年级查班级
            List<ClassInfo> classInfo = classServiceImpl.selectListByClassGrade(claInfo.getClassGrade());
//            List<CascaderChild> children = new ArrayList<>();
            System.out.println("classInfo = " + classInfo);
            for (ClassInfo c :
                    classInfo) {
                CascaderChild cascaderChild = new CascaderChild();
                cascaderChild.setLabel(c.getClassName());
                cascaderChild.setValue(c.getId().toString());
                cascaderParent.getChildren().add(cascaderChild);
            }
            list.add(cascaderParent);
        }
        return list;
    }

    //根据InsId查找辅导员所有信息，并且查找所带班级
    @Override
    public InsInfoVO getInsInfo(Long id) {
        Instructor instructor = baseMapper.selectById(id);
        InsInfoVO insInfoVO = new InsInfoVO();
        BeanUtil.copyProperties(instructor,insInfoVO);
        List<ClassInfo> classInfos = classServiceImpl.selectClassListByInstId(id);
        insInfoVO.setClassList(new ArrayList<>());
        for (ClassInfo c :
                classInfos) {
            insInfoVO.getClassList().add(c.getClassName());
        }
        return insInfoVO;
    }

    //删除 辅导员
    @Override
    public boolean deleteInstById(String id) {
        Long ID = new Long(id);
        int i = baseMapper.deleteById(ID);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<Info_classVo> selectInsName() {
        QueryWrapper wrapper = new QueryWrapper();
        List<Instructor> list =  baseMapper.selectList(wrapper);
        List<Info_classVo> list1 = new ArrayList<>();
        Info_classVo info_classVo = new Info_classVo();
        info_classVo.setValue("0");
        info_classVo.setLabel("未分配");
        list1.add(info_classVo);
        for (Instructor in :
                list) {
            Info_classVo classInfo = new Info_classVo();
            classInfo.setLabel(in.getInsName());
            classInfo.setValue(in.getId().toString());
            list1.add(classInfo);
        }
        return list1;
    }

    @Override
    public boolean updateIns(Instructor instructor) {
        if(instructor.getInsName()!=""&&instructor.getInsAddress()!=""&&instructor.getAccount().length()==11&&instructor.getInsPhoneNumber().length()==11){
            try{
                Long account = new Long(instructor.getAccount());
                Long phone = new Long(instructor.getInsPhoneNumber());
                System.out.println("phone =================" );
            }catch (NumberFormatException e){
                System.out.println("catch =================" );
                return false;
            }
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("account",instructor.getAccount());
            Instructor i = baseMapper.selectOne(wrapper);
            if(i!=null){
                System.out.println("不等于空 ============== ");
                if(instructor.getId().equals(i.getId())){
                    int j = baseMapper.updateById(instructor);
                    System.out.println("j ============== " + j);
                    if(j==1){
                        return true;
                    }
                }
                return false;
            }else {
                int j = baseMapper.updateById(instructor);
                if(j==1){
                    return true;
                }
            }
        }
        System.out.println("else====================");
        return false;
    }

    @Override
    public String selectIDByAcc(String acc) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("account",acc);

        return baseMapper.selectOne(wrapper).getId().toString();
    }

    @Override
    public Instructor selectInsInfo(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",new Long(id));
        Instructor instructor = baseMapper.selectOne(wrapper);
        return instructor;
    }

    @Override
    public int updateInsByInsId(Instructor instructor) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",instructor.getId());
        Instructor ins = baseMapper.selectOne(wrapper);
        if(!ins.getInsPassword().equals(instructor.getInsPassword())){
            instructor.setInsPassword(JiaMi.jiami(instructor.getInsPassword()));
        }
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.ne("id",instructor.getId());
        wrapper1.eq("ins_phone_number",instructor.getInsPhoneNumber());

        List<InsService> list =  baseMapper.selectList(wrapper1);
        if (list.size()>0){
            return 0;
        }
        instructor.setAccount(instructor.getInsPhoneNumber());
        int i = baseMapper.updateById(instructor);
        if(i==1){
            return 1;
        }
        return 0;
    }

    @Override
    @Cacheable(cacheNames = "selectInsNameByID")
    public String selectInsNameByID(Long classInsId) {
        Instructor in = baseMapper.selectById(classInsId);
        if(in==null){
            return "未分配";
        }
        return in.getInsName();
    }

    //分页
    @Override
    public Map<Object,Object> selectPage(int num, int size, InsVo insVo) {

        System.out.println("num = " + num+"size"+size);
        Page<Instructor> page = new Page<>(num,size);
        QueryWrapper wrapper = new QueryWrapper();
        if(insVo!=null){
            if(insVo.getInsName()!=""){
                wrapper.like("ins_name",insVo.getInsName());
            }
            if(insVo.getInsPhoneNumber()!=""){
                wrapper.like("ins_phone_number",insVo.getInsPhoneNumber());
            }
            if(insVo.getInsClass()!=""){
                Long insID =  classServiceImpl.selectInsIdByClassId(insVo.getInsClass());
                wrapper.eq("id",insID);
            }
        }
        baseMapper.selectPage(page,wrapper);
        Page<Instructor> page1 = page;
        page.setTotal(baseMapper.selectCount(wrapper));

        System.out.println("page = " + page1.getRecords().toString());
        Map<Object,Object> map = new HashMap<>();
        map.put("page",page);
        Map<String,Integer> list = new HashMap<>();
        Iterator<Instructor> iterator = page1.getRecords().iterator();
        while (iterator.hasNext()){
            Instructor ins = iterator.next();
            int sum = classServiceImpl.selectClassNumByInsId(ins.getId());
            list.put(ins.getId().toString(),sum);
            System.out.println("ins.getId() = " + ins.getId());
        }
        map.put("list",list);

        return map;
    }
}
