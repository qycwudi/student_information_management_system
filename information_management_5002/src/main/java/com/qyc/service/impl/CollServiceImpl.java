package com.qyc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.ClassInfo;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.qyc.entity.InsInfoVO;
import com.qyc.entity.InsVo;
import com.qyc.mapper.CollMapper;
import com.qyc.service.CollService;
import com.util.JiaMi;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author qyc
 * @time 2020/11/18 - 16:05
 */
@Service
public class CollServiceImpl extends ServiceImpl<CollMapper, CollegeAdmin> implements CollService {
    @Override
    public CollegeAdmin selectAdminInfo(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",new Long(id));
        CollegeAdmin collegeAdmin = baseMapper.selectOne(wrapper);
        return collegeAdmin;
    }

    @Override
    public boolean updateCollById(CollegeAdmin collegeAdmin) {

        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.ne("id",collegeAdmin.getId());
        wrapper1.eq("account",collegeAdmin.getAccount());
        List<CollegeAdmin> list1 = new ArrayList<>();
        list1 = baseMapper.selectList(wrapper1);
        if(list1.size()>0){
            return false;
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",collegeAdmin.getId());
        CollegeAdmin collegeAdmin1 = baseMapper.selectOne(wrapper);

        if(!collegeAdmin1.getCollAdmPassword().equals(collegeAdmin.getCollAdmPassword())){
            collegeAdmin.setCollAdmPassword(JiaMi.jiami(collegeAdmin.getCollAdmPassword()));
        }
        if(baseMapper.updateById(collegeAdmin)==1){
            return true;
        }
        return false;
    }

    @Override
    public CollegeAdmin selectCollInfo(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",new Long(id));
        CollegeAdmin collegeAdmin = baseMapper.selectOne(wrapper);
        return collegeAdmin;
    }

    @Override
    public Map<Object, Object> selectPage(int num, int size, InsVo insVo) {
        System.out.println("num = " + num+"size"+size);
        Page<CollegeAdmin> page = new Page<>(num,size);
        QueryWrapper wrapper = new QueryWrapper();
        if(insVo!=null){
            if(insVo.getInsName()!=""){
                wrapper.like("coll_adm_name",insVo.getInsName());
            }
            if(insVo.getInsPhoneNumber()!="") {
                wrapper.like("coll_adm_phone_number", insVo.getInsPhoneNumber());
            }
        }
        baseMapper.selectPage(page,wrapper);
        Map<Object,Object> map = new HashMap<>();
        map.put("page",page);
        return map;
    }

    @Override
    public boolean insertColl(CollegeAdmin collegeAdmin) {
        collegeAdmin.setRole("0");
        if (collegeAdmin.getCollAdmName() != "" && collegeAdmin.getCollAdmPosition() != "" && collegeAdmin.getCollAdmPhoneNumber().length() == 11&&collegeAdmin.getAccount().length()==6) {
            try{
                Long phone = new Long(collegeAdmin.getCollAdmPhoneNumber());
                Long account = new Long(collegeAdmin.getAccount());
            }catch (NumberFormatException e){
                return false;
            }

            QueryWrapper wrapper = new QueryWrapper();
//            wrapper.eq("coll_adm_phone_number", collegeAdmin.getCollAdmPhoneNumber());
            wrapper.eq("account", collegeAdmin.getAccount());
            CollegeAdmin collegeAdmin1 = baseMapper.selectOne(wrapper);
            System.out.println("instructor1 = " + collegeAdmin1);
            if (collegeAdmin1 != null) {
                return false;
            }
            collegeAdmin.setCollAdmPassword("a2abd179ebd09a63a77a7aa74981f2a4");
            int i = baseMapper.insert(collegeAdmin);
            if (i == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CollegeAdmin getCollInfo(Long id) {
        CollegeAdmin collegeAdmin = baseMapper.selectById(id);
        return collegeAdmin;
    }

    @Override
    public boolean updateColl(CollegeAdmin collegeAdmin) {
        if(collegeAdmin.getCollAdmName()!=""&&collegeAdmin.getCollAdmPosition()!=""&&collegeAdmin.getAccount().length()==6&&collegeAdmin.getCollAdmPhoneNumber().length()==11){
            try{
                Long account = new Long(collegeAdmin.getAccount());
                Long phone = new Long(collegeAdmin.getCollAdmPhoneNumber());
                System.out.println("phone =================" );
            }catch (NumberFormatException e){
                System.out.println("catch =================" );
                return false;
            }
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("account",collegeAdmin.getAccount());
            wrapper.eq("coll_adm_phone_number",collegeAdmin.getCollAdmPhoneNumber());
            CollegeAdmin i = baseMapper.selectOne(wrapper);
            if(i!=null){
                System.out.println("不等于空 ============== ");
                if(collegeAdmin.getId().equals(i.getId())){
                    int j = baseMapper.updateById(collegeAdmin);
                    if(j==1){
                        return true;
                    }
                }
                return false;
            }else {
                int j = baseMapper.updateById(collegeAdmin);
                if(j==1){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteColl(String id) {
        Long ID = new Long(id);
        int i = baseMapper.deleteById(ID);
        if(i==1){
            return true;
        }
        return false;
    }
}
