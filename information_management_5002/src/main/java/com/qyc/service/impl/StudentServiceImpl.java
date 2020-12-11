package com.qyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.StudentMessage;
import com.qyc.entity.InsVo;
import com.qyc.entity.StuExcel;
import com.qyc.entity.StuVO;
import com.qyc.entity.StudentMessageVO;
import com.qyc.mapper.StudentMapper;
import com.qyc.service.StudentService;
import com.qyc.service.StudentStateService;
import com.util.JiaMi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qyc
 * @time 2020/11/19 - 20:34
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, StudentMessage> implements StudentService
{
    @Override
    public int updateStuByPhone(StudentMessageVO studentMessageVO) {
        String id = studentMessageVO.getId();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",new Long(id));
        StudentMessage studentMessage = baseMapper.selectOne(wrapper);
        studentMessage.setStuPhoneNumber(studentMessageVO.getStuPhoneNumber());

        if(!studentMessage.getStuPassword().equals(studentMessageVO.getStuPassword())){
            studentMessage.setStuPassword(JiaMi.jiami(studentMessageVO.getStuPassword()));
        }

        if(studentMessageVO.getImgUrl()!=""||studentMessageVO.getImgUrl()!=null){
            studentMessage.setStuImgUrl(studentMessageVO.getImgUrl());
            studentMessage.setStuStateInfoId("4");
        }
        return baseMapper.updateById(studentMessage);
    }

    //    批量添加
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean addAll(List<StuExcel> read) {
        int yb = 0;
        try{
            for (StuExcel s :
                    read) {
                if(s.getName()!=null&&s.getName()!=""&&s.getSex()!=null&&s.getSex()!=""&&
                s.getMz()!=null&&s.getMz()!=""&&s.getAccount()!=null&&s.getAccount()!=""&&
                        s.getNj()!=null&&s.getNj()!=""&&s.getZy()!=null&&s.getZy()!=""&&
                        s.getBj()!=null&&s.getBj()!=""&&s.getZzmm()!=null&&s.getZzmm()!=""&&
                        s.getLxdh()!=null&&s.getLxdh()!=""&&s.getFdyId()!=null&&s.getFdyId()!="") {

                    QueryWrapper wrapper = new QueryWrapper();
                    wrapper.eq("account",s.getAccount());
                    StudentMessage studentMessage1 = baseMapper.selectOne(wrapper);
                    if (studentMessage1!=null){
                        throw new RuntimeException();
                    }
                    StudentMessage studentMessage = new StudentMessage();
                    studentMessage.setStuName(s.getName());
                    studentMessage.setStuSex(s.getSex());
                    studentMessage.setStuNation(s.getMz());
                    studentMessage.setAccount(s.getAccount());
                    studentMessage.setStuGrade(s.getNj());
                    studentMessage.setStuMajor(s.getZy());
                    studentMessage.setStuClassId(new Long(classService.selectClassIdByClassName(s.getBj())));
                    studentMessage.setStuPoliticsStatus(s.getZzmm());
                    studentMessage.setStuPhoneNumber(s.getLxdh());
                    studentMessage.setStuInsId(new Long(s.getFdyId()));
                    studentMessage.setRole("3");
                    studentMessage.setStuIdCard(s.getSfzh());
                    studentMessage.setStuStateId("1");
                    studentMessage.setStuStateInfoId("1");
                    studentMessage.setStuPassword("a2abd179ebd09a63a77a7aa74981f2a4");

                    int i = baseMapper.insert(studentMessage);
                    yb++;
                    if(i!=1){
                        throw new Exception();
                    }
                }else {
                    throw new Exception();
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }return true;
    }

    @Autowired
    private ClassServiceImpl classService;

    @Autowired
    private InsServiceImpl insService;

    @Autowired
    private StudentStateServiceImpl studentStateService;

    @Autowired
    private StudentMessageStateServiceImpl studentMessageStateService;

    @Override
    public void updateInsIdByClassId(Long classId, Long insId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("stu_class_id",classId);
        List<StudentMessage> list = baseMapper.selectList(wrapper);
        for (StudentMessage  s : list
        ) {
            s.setStuInsId(insId);
            baseMapper.updateById(s);
        }
    }

    @Override
    public StudentMessage selectStyById(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",new Long(id));
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int updateByStu(StudentMessage studentMessage) {
        if (studentMessage.getStuDuty()!=null&&studentMessage.getStuDuty().equals("班长")){
            studentMessage.setRole("4");
        }
        return baseMapper.updateById(studentMessage);
    }

    @Override
    public StudentMessage selectStuByAcc(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("account",name);
        StudentMessage studentMessage = baseMapper.selectOne(wrapper);
        if(studentMessage!=null){
            return studentMessage;
        }else {
            return null;
        }
    }

    @Override
    public StudentMessageVO selectStuInfo(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",new Long(id));
        StudentMessage s = baseMapper.selectOne(wrapper);
        StudentMessageVO studentMessageVO = new StudentMessageVO();
        studentMessageVO.setStuPassword(s.getStuPassword());
        studentMessageVO.setImgUrl(s.getStuImgUrl());
        studentMessageVO.setId(s.getId().toString());
        studentMessageVO.setStuName(s.getStuName());
        studentMessageVO.setStuSex(s.getStuSex());
        studentMessageVO.setAccount(s.getAccount());
        studentMessageVO.setStuIdCard(s.getStuIdCard());
        studentMessageVO.setStuMajor(s.getStuMajor());
        studentMessageVO.setStuPhoneNumber(s.getStuPhoneNumber());
        studentMessageVO.setStuNation(s.getStuNation());
        studentMessageVO.setStuPoliticsStatus(s.getStuPoliticsStatus());
        studentMessageVO.setStuDuty(s.getStuDuty());
        studentMessageVO.setStuInsName(insService.selectInsNameByID(s.getStuInsId()));
        studentMessageVO.setStuState(studentStateService.getStateByID(s.getStuStateId()));
        studentMessageVO.setStuStateInfo(studentMessageStateService.getStateById(s.getStuStateInfoId()));
        studentMessageVO.setStuClassName(classService.selectClassNameByClassId(s.getStuClassId()));

        return studentMessageVO;
    }

    @Override
    public Map<String, Object> selectPage(StuVO insVo) {
        QueryWrapper wrapper = new QueryWrapper();
        //线程安全的list
        List<StudentMessageVO> list = Collections.synchronizedList(new ArrayList<>());
        if(insVo.getStuName()!=null&&insVo.getStuName()!=""){
            wrapper.like("stu_name",insVo.getStuName());
        }
        if(insVo.getStuAccount()!=null&&insVo.getStuAccount()!=""){
            wrapper.like("account",insVo.getStuAccount());
        }
        if(insVo.getStuMajor()!=null&&insVo.getStuName()!=""){
            wrapper.eq("stu_major",insVo.getStuMajor());
        }
        if(insVo.getStuGrade()!=null&&insVo.getStuGrade()!=""){
            wrapper.eq("stu_grade",insVo.getStuGrade());
        }
        if(insVo.getStuClassId()!=null&&insVo.getStuClassId()!=""){
            wrapper.eq("stu_class_id",insVo.getStuClassId());
        }


        List<StudentMessage> list1 = baseMapper.selectList(wrapper);
        CopyOnWriteArrayList<StudentMessage> listC = new CopyOnWriteArrayList<>(list1);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (StudentMessage s :
                listC) {
            pool.submit(()->{
                try {
                    StudentMessageVO studentMessageVO = new StudentMessageVO();
                    studentMessageVO.setId(s.getId().toString());
                    studentMessageVO.setStuName(s.getStuName());
                    studentMessageVO.setStuSex(s.getStuSex());
                    studentMessageVO.setAccount(s.getAccount());
                    studentMessageVO.setStuIdCard(s.getStuIdCard());
                    studentMessageVO.setStuMajor(s.getStuMajor());
                    studentMessageVO.setStuPhoneNumber(s.getStuPhoneNumber());
                    studentMessageVO.setStuNation(s.getStuNation());
                    studentMessageVO.setStuPoliticsStatus(s.getStuPoliticsStatus());
                    studentMessageVO.setStuDuty(s.getStuDuty());
                    //整合    Redis
                    studentMessageVO.setStuInsName(insService.selectInsNameByID(s.getStuInsId()));
                    studentMessageVO.setStuState(studentStateService.getStateByID(s.getStuStateId()));
                    studentMessageVO.setStuStateInfo(studentMessageStateService.getStateById(s.getStuStateInfoId()));
                    studentMessageVO.setStuClassName(classService.selectClassNameByClassId(s.getStuClassId()));
                    list.add(studentMessageVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }pool.shutdown();
        while (true) {
            if(pool.isTerminated()){
                System.out.println(" 结束 ");
                Map<String, Object> map = new HashMap<>();
                map.put("pageList",list);
                map.put("total",list.size());
                return map;
            }
        }
    }

    @Override
    public StudentMessage selectByWrapper(QueryWrapper wrapper1) {

        return baseMapper.selectOne(wrapper1);
    }

    @Override
    public StudentMessage selectStyByIdAndStuVo(String schStuId, StuVO stuVO) {
        QueryWrapper wrapper1 = new QueryWrapper();
        //专业
        if(stuVO.getStuMajor()!=null&&stuVO.getStuMajor()!=""){
            wrapper1.eq("stu_major",stuVO.getStuMajor());
        }
        //年级
        if(stuVO.getStuGrade()!=null&&stuVO.getStuGrade()!=""){
            wrapper1.eq("stu_grade",stuVO.getStuGrade());
        }
        //acc
        if(stuVO.getStuAccount()!=null&&stuVO.getStuAccount()!=""){
            wrapper1.like("account",stuVO.getStuAccount());
        }
        wrapper1.eq("id",new Long(schStuId));
        StudentMessage studentMessage = new StudentMessage();
        studentMessage = baseMapper.selectOne(wrapper1);
        System.out.println("student============= = " + studentMessage);
        return studentMessage;
    }

    @Override
    public List<String> selectStuClassIdByInsId(String acc) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("stu_class_id");
        wrapper.eq("stu_ins_id",new Long(acc));
        List<String> list = new ArrayList<>();
        list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public boolean insertStu(StudentMessage studentMessage) {

        if(studentMessage.getStuName()!=null&&
        studentMessage.getStuClassId()!=null&&
        studentMessage.getStuMajor()!=null&&
        studentMessage.getStuGrade()!=null&&
        studentMessage.getStuIdCard()!=null
        &&studentMessage.getStuMajor()!=null
        &&studentMessage.getAccount().length()==10&&
        studentMessage.getStuIdCard().length()==18&&
        studentMessage.getStuPhoneNumber().length()==11){
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("account",studentMessage.getAccount());
            if(baseMapper.selectOne(wrapper)!=null){
                return false;
            }
            System.out.println("进入");
            studentMessage.setRole("3");
            studentMessage.setStuInsId(classService.selectInsIdByClassId(studentMessage.getStuClassId().toString()));
            studentMessage.setStuStateInfoId("1");
            studentMessage.setStuStateId("1");
            studentMessage.setStuPassword("a2abd179ebd09a63a77a7aa74981f2a4");
            int  i = baseMapper.insert(studentMessage);
            if (i == 1) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Map<String, Object> selectPageBybanzhang(String acc) {
        QueryWrapper wrapper2 = new QueryWrapper();
        wrapper2.eq("account",acc);
        StudentMessage banzhang = baseMapper.selectOne(wrapper2);
        Long classID = banzhang.getStuClassId();
        QueryWrapper wrapper = new QueryWrapper();
        //线程安全的list
        List<StudentMessageVO> list = Collections.synchronizedList(new ArrayList<>());
        wrapper.eq("stu_class_id",classID);
        List<StudentMessage> list1 = baseMapper.selectList(wrapper);
        CopyOnWriteArrayList<StudentMessage> listC = new CopyOnWriteArrayList<>(list1);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (StudentMessage s :
                listC) {
            pool.submit(()->{
                try {
                    StudentMessageVO studentMessageVO = new StudentMessageVO();
                    studentMessageVO.setId(s.getId().toString());
                    studentMessageVO.setStuName(s.getStuName());
                    studentMessageVO.setStuSex(s.getStuSex());
                    studentMessageVO.setAccount(s.getAccount());
                    studentMessageVO.setStuIdCard(s.getStuIdCard());
                    studentMessageVO.setStuMajor(s.getStuMajor());
                    studentMessageVO.setStuPhoneNumber(s.getStuPhoneNumber());
                    studentMessageVO.setStuNation(s.getStuNation());
                    studentMessageVO.setStuPoliticsStatus(s.getStuPoliticsStatus());
                    studentMessageVO.setStuDuty(s.getStuDuty());
                    //整合    Redis
                    studentMessageVO.setStuInsName(insService.selectInsNameByID(s.getStuInsId()));
                    studentMessageVO.setStuState(studentStateService.getStateByID(s.getStuStateId()));
                    studentMessageVO.setStuStateInfo(studentMessageStateService.getStateById(s.getStuStateInfoId()));
                    studentMessageVO.setStuClassName(classService.selectClassNameByClassId(s.getStuClassId()));
                    list.add(studentMessageVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
        while (true) {
            if(pool.isTerminated()){
                System.out.println(" 结束 ");
                Map<String, Object> map = new HashMap<>();
                map.put("pageList",list);
                map.put("total",list.size());
                return map;
            }
        }
    }
    @Override
    public Map<String, Object> selectPageByins(String acc, StuVO insVo) {

        String insID = insService.selectIDByAcc(acc);

        QueryWrapper wrapper = new QueryWrapper();
        //线程安全的list
        List<StudentMessageVO> list = Collections.synchronizedList(new ArrayList<>());
        if(insVo.getStuName()!=null&&insVo.getStuName()!=""){
            wrapper.like("stu_name",insVo.getStuName());
        }
        if(insVo.getStuAccount()!=null&&insVo.getStuAccount()!=""){
            wrapper.like("account",insVo.getStuAccount());
        }
        if(insVo.getStuMajor()!=null&&insVo.getStuName()!=""){
            wrapper.eq("stu_major",insVo.getStuMajor());
        }
        if(insVo.getStuGrade()!=null&&insVo.getStuGrade()!=""){
            wrapper.eq("stu_grade",insVo.getStuGrade());
        }
        if(insVo.getStuClassId()!=null&&insVo.getStuClassId()!=""){
            wrapper.eq("stu_class_id",insVo.getStuClassId());
        }
        wrapper.eq("stu_ins_id",insID);
        List<StudentMessage> list1 = baseMapper.selectList(wrapper);
        CopyOnWriteArrayList<StudentMessage> listC = new CopyOnWriteArrayList<>(list1);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (StudentMessage s :
                listC) {
            pool.submit(()->{
                try {
                    StudentMessageVO studentMessageVO = new StudentMessageVO();
                    studentMessageVO.setId(s.getId().toString());
                    studentMessageVO.setStuName(s.getStuName());
                    studentMessageVO.setStuSex(s.getStuSex());
                    studentMessageVO.setAccount(s.getAccount());
                    studentMessageVO.setStuIdCard(s.getStuIdCard());
                    studentMessageVO.setStuMajor(s.getStuMajor());
                    studentMessageVO.setStuPhoneNumber(s.getStuPhoneNumber());
                    studentMessageVO.setStuNation(s.getStuNation());
                    studentMessageVO.setStuPoliticsStatus(s.getStuPoliticsStatus());
                    studentMessageVO.setStuDuty(s.getStuDuty());
                    //整合    Redis
                    studentMessageVO.setStuInsName(insService.selectInsNameByID(s.getStuInsId()));
                    studentMessageVO.setStuState(studentStateService.getStateByID(s.getStuStateId()));
                    studentMessageVO.setStuStateInfo(studentMessageStateService.getStateById(s.getStuStateInfoId()));
                    studentMessageVO.setStuClassName(classService.selectClassNameByClassId(s.getStuClassId()));
                    list.add(studentMessageVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }pool.shutdown();
        while (true) {
            if(pool.isTerminated()){
                System.out.println(" 结束 ");
                Map<String, Object> map = new HashMap<>();
                map.put("pageList",list);
                map.put("total",list.size());
                return map;
            }
        }
    }
}
