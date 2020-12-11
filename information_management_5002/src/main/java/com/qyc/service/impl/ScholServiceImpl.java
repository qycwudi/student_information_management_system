package com.qyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.ScholarshipInfo;
import com.bean.StudentMessage;
import com.qyc.entity.CascaderChild;
import com.qyc.entity.StuVO;
import com.qyc.mapper.ScholMapper;
import com.qyc.service.ScholService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qyc
 * @time 2020/11/25 - 15:21
 */
@Service
public class ScholServiceImpl extends ServiceImpl<ScholMapper, ScholarshipInfo> implements ScholService {
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private InsServiceImpl insService;


    @Autowired
    private ScholTypeServiceImpl scholTypeService;

    @Override
    public int updateStateBYInsTG(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sch_stu_id",id);
        wrapper.eq("sch_age_limit",new SimpleDateFormat("yyyy").format(new Date()));
        ScholarshipInfo scholarshipInfo = baseMapper.selectOne(wrapper);
        scholarshipInfo.setSchStateId("2");
        return baseMapper.updateById(scholarshipInfo);
    }

    @Override
    public int updateStateBYInsJj(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sch_stu_id",id);
        wrapper.eq("sch_age_limit",new SimpleDateFormat("yyyy").format(new Date()));
        ScholarshipInfo scholarshipInfo = baseMapper.selectOne(wrapper);
        scholarshipInfo.setSchStateId("4");
        return baseMapper.updateById(scholarshipInfo);
    }

    @Autowired
    private ClassServiceImpl classService;
    @Override  //状态，班级ID，年级，学号，专业
    public List<StudentMessage> selectCollList(String state, String value, StuVO stuVO) {
        Map<String,String> map1 = new HashMap<>();
        map1.put("1","国家奖学金");
        map1.put("2","励志奖学金");
        map1.put("3","一等奖学金");
        map1.put("4","二等奖学金");
        map1.put("5","三等奖学金");
        QueryWrapper wrapper = new QueryWrapper();
        if(state.equals("undefined")){
            state = "2";
        }
        wrapper.eq("sch_state_id",state);
        if(!value.equals("undefined")){
            wrapper.eq("sch_class_id",value);
        }
        List<ScholarshipInfo> list = baseMapper.selectList(wrapper);
        System.out.println("list=========== " + list);
        List<StudentMessage> list1 = new ArrayList<>();
        for (ScholarshipInfo s :
                list) {

            QueryWrapper wrapper1 = new QueryWrapper();
            if(stuVO!=null){

            StudentMessage studentMessage = studentService.selectStyByIdAndStuVo(s.getSchStuId(),stuVO);

                System.out.println("studentMessage ======== " + studentMessage);
            if(studentMessage!=null){
                studentMessage.setStuIdCard(map1.get(s.getSchTypeId()));
                studentMessage.setStuSex(classService.selectClassNameByClassId(new Long(s.getSchClassId())));
                list1.add(studentMessage);
            }

        }
        }
        return list1;
    }


//    coll通过
    @Override
    public boolean updateSchStateByCollTG(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sch_stu_id",id);
        ScholarshipInfo s = baseMapper.selectOne(wrapper);
        s.setSchStateId("5");
        if(baseMapper.updateById(s)==1){
            return true;
        }
        return false;
    }
//  coll拒绝
    @Override
    public boolean updateSchStateJJ(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sch_stu_id",id);
        ScholarshipInfo s = baseMapper.selectOne(wrapper);
        s.setSchStateId("6");
        if(baseMapper.updateById(s)==1){
            return true;
        }
        return false;
    }

    @Override
    public List<StudentMessage> showInstListByVo(String acc, String state, String classID) {

        String Id = insService.selectIDByAcc(acc);
//        {value: '1',label: '待审核'},
//        {value: '2',label: '通过/待学院审核'},
//        {value: '3',label: '未通过'}
        QueryWrapper wrapper = new QueryWrapper();
        if(!state.equals("undefined")){
            if(state.equals("1")){
                wrapper.eq("sch_state_id","1");
            }
            if(state.equals("2")){
                wrapper.eq("sch_state_id","2");
            }
            if(state.equals("3")){
                wrapper.eq("sch_state_id","4");
            }
        }else {
            wrapper.eq("sch_state_id","1");
        }
       if(!classID.equals("undefined")){
           wrapper.eq("sch_class_id",classID);
       }
        wrapper.eq("sch_ins_id",Id);
        List<ScholarshipInfo> list = baseMapper.selectList(wrapper);
        List<StudentMessage> listStu = new ArrayList<>();
        if(list!=null){
            for (ScholarshipInfo sch:
                    list ) {
                StudentMessage studentMessage1 = studentService.selectStyById(sch.getSchStuId());
                studentMessage1.setStuIdCard(scholTypeService.selectTypeNameById(sch.getSchTypeId()));
                listStu.add(studentMessage1);
            }
            return listStu;
        }
        return null;
    }

    @Override
    public List<StudentMessage> showInstList(String acc) {

//        List<String> classID = studentService.selectStuClassIdByInsId(acc);
//        if(classID!=null){
        String Id = insService.selectIDByAcc(acc);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("sch_state_id","1");
            wrapper.eq("sch_ins_id",Id);
        List<ScholarshipInfo> list = baseMapper.selectList(wrapper);
        List<StudentMessage> listStu = new ArrayList<>();
        if(list!=null){
            for (ScholarshipInfo sch:
                    list ) {
                StudentMessage studentMessage1 = studentService.selectStyById(sch.getSchStuId());
                studentMessage1.setStuIdCard(scholTypeService.selectTypeNameById(sch.getSchTypeId()));
                listStu.add(studentMessage1);
        }
        return listStu;
    }
        return null;
    }

    @Override
    public int updateSchState(boolean value) {
        ScholarshipInfo scholarshipInfo = baseMapper.selectById(1);
        System.out.println("value = " + value);
        if(value){
            scholarshipInfo.setSchStuId("1");
        }else {
            scholarshipInfo.setSchStuId("2");
        }
        int i = baseMapper.updateById(scholarshipInfo);
        return i;
    }



    @Override
    public boolean collschJc() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",1);
        wrapper.select("sch_stu_id");
        String i = baseMapper.selectOne(wrapper).getSchStuId();
        if(i.equals("1")){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public List<StudentMessage> showbanzhangList(String acc) {
        StudentMessage studentMessage = studentService.selectStuByAcc(acc);
        if(studentMessage!=null){
            String classId = studentMessage.getStuClassId().toString();
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("sch_class_id",classId);
            wrapper.eq("sch_state_id","7");
            List<ScholarshipInfo> list = baseMapper.selectList(wrapper);
            if(list!=null){
                List<StudentMessage> listStu = new ArrayList<>();
                for (ScholarshipInfo sch:
                        list ) {
                    StudentMessage studentMessage1 = studentService.selectStyById(sch.getSchStuId());
                    studentMessage1.setStuIdCard(scholTypeService.selectTypeNameById(sch.getSchTypeId()));
                    listStu.add(studentMessage1);
                }

                return listStu;
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public int updateStateBYbanzhang(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sch_stu_id",id);
        wrapper.eq("sch_age_limit",new SimpleDateFormat("yyyy").format(new Date()));
        ScholarshipInfo scholarshipInfo = baseMapper.selectOne(wrapper);
        scholarshipInfo.setSchStateId("1");

        return baseMapper.updateById(scholarshipInfo);
    }

    @Override
    public int addSch(String value, String name) {
        StudentMessage studentMessage = studentService.selectStuByAcc(name);
        if(!value.equals("undefined")){
            if(studentMessage!=null){
                if(collschJc()){
                    if(schJc(name)){
                        ScholarshipInfo scholarshipInfo = new ScholarshipInfo();
                        scholarshipInfo.setSchInsId(studentMessage.getStuInsId().toString());
                        scholarshipInfo.setSchStuId(studentMessage.getId().toString());
                        scholarshipInfo.setSchTypeId(value);
                        scholarshipInfo.setSchStateId("7");
                        scholarshipInfo.setSchClassId(studentMessage.getStuClassId().toString());
                        scholarshipInfo.setSchAgeLimit(new SimpleDateFormat("yyyy").format(new Date()));
                        int i = baseMapper.insert(scholarshipInfo);
                        if(i == 1){
                            return 1;
                        }else {
                            return 0;

                        }
                    }

                }else {
                    return 0;
                }

            }else {
                return 0;
            }
        }else {
            return 0;
        }
        return 0;

    }

    @Override
    public boolean schJc(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",1);
        wrapper.select("sch_stu_id");
        String i = baseMapper.selectOne(wrapper).getSchStuId();
        if(i.equals("1")){
            StudentMessage studentMessage = studentService.selectStuByAcc(id);
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("sch_stu_id",studentMessage.getId().toString());
            wrapper1.eq("sch_age_limit",new SimpleDateFormat("yyyy").format(new Date()));
            if(baseMapper.selectOne(wrapper1)!=null){
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<ScholarshipInfo> selectJlByStuAcc(String acc) {
        StudentMessage studentMessage = studentService.selectStuByAcc(acc);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sch_stu_id",studentMessage.getId().toString());
        wrapper.orderByDesc("sch_age_limit");
        List<ScholarshipInfo> list = baseMapper.selectList(wrapper);
        if(list==null){
            return null;
        }else {
            Map<String,String> map = new HashMap<>();
            map.put("1","待导员审核");
            map.put("2","待学院审核");
            map.put("7","待班长审核");
            map.put("3","导员审核通过");
            map.put("4","导员审核不通过");
            map.put("5","学院审核通过");
            map.put("6","学院审核不通过");
            map.put("8","班长审核通过");
            Map<String,String> map1 = new HashMap<>();
            map1.put("1","国家奖学金");
            map1.put("2","励志奖学金");
            map1.put("3","一等奖学金");
            map1.put("4","二等奖学金");
            map1.put("5","三等奖学金");
            for(int i =0;i<list.size();i++){
                list.get(i).setSchStateId(map.get(list.get(i).getSchStateId()));
                list.get(i).setSchTypeId(map1.get(list.get(i).getSchTypeId()));
            }
            return list;
        }

    }
}
