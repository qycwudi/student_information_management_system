package com.qyc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.ClassInfo;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.qyc.entity.ClassInfoVo;
import com.qyc.entity.ClassSelectVO;
import com.qyc.entity.Info_classVo;
import com.qyc.entity.InsVo;
import com.qyc.service.impl.ClassServiceImpl;
import com.qyc.service.impl.CollServiceImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/19 - 20:27
 */
@RestController
@CrossOrigin
@RequestMapping("ins/class")
public class ClassController {
    @Autowired
    private ClassServiceImpl classServiceImpl;

    @PostMapping("showClassPage/{num}/{size}/{value}")
    public R showClassPage(@PathVariable("num") int num, @PathVariable("size") int size,@PathVariable("value") String value,@RequestBody ClassSelectVO insVo){
        System.out.println("insVo = " + insVo);
        if (!value.equals("undefined")){
            insVo.setInsID(value);
        }
        Map<String,Object> map = classServiceImpl.selectPage(num,size,insVo);
        return R.ok().data("instInfoList",map.get("pageList")).data("num",map.get("num")).data("size",map.get("size")).data("total",map.get("total"));
    }
    @PostMapping("insertClass")
    public R insertColl(@RequestBody ClassInfo info){
        if(classServiceImpl.insertColl(info)){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }

    @DeleteMapping("deleteClass/{id}")
    public R deleteColl(@PathVariable("id") String id){

        if(classServiceImpl.deleteClass(id)){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

    @GetMapping("selectIns")
    public R selectIns(){
        List<Info_classVo> list = classServiceImpl.selectIns();
        return R.ok().data("insNameList",list);
    }

    @GetMapping("showClass/{id}")
    public R showClass(@PathVariable("id") String id){
        Long ID = new Long(id);
        ClassInfo classInfo = classServiceImpl.selectClassInfo(ID);
        return R.ok();
    }

    @PostMapping("bianjiClass")
    public R bianjiColl(@RequestBody ClassInfoVo classInfo){

        ClassInfo classInfo1 = new ClassInfo();
        classInfo1.setId(classInfo.getId());
        classInfo1.setClassStuSum(classInfo.getClassStuSum());
        classInfo1.setClassGrade(classInfo.getClassGrade());
        classInfo1.setClassName(classInfo.getClassName());
        classInfo1.setClassInsId(new Long(classInfo.getClassInsName()));
        boolean b = classServiceImpl.updateClass(classInfo1);
        if (b){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

}
