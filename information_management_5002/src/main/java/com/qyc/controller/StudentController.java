package com.qyc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.bean.StudentMessage;
import com.qyc.entity.InsVo;
import com.qyc.entity.StuVO;
import com.qyc.entity.StudentMessageVO;
import com.qyc.service.impl.CollServiceImpl;
import com.qyc.service.impl.StudentMessageStateServiceImpl;
import com.qyc.service.impl.StudentServiceImpl;
import com.qyc.service.impl.StudentStateServiceImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/19 - 20:32
 */
@RestController
@CrossOrigin
@RequestMapping("ins/stu")
public class StudentController {
    @Autowired
    private StudentServiceImpl studentService;


    @PostMapping("showStuPage")
    public R showCollPage(@RequestBody StuVO stuVO){
        System.out.println("stuVO----------***********-------- = " + stuVO);
        Map<String,Object> map = studentService.selectPage(stuVO);
        return R.ok().data("instInfoList",map.get("pageList")).data("total",map.get("total"));
    }

    @Autowired
    private StudentMessageStateServiceImpl studentMessageStateService;
    @Autowired
    private StudentStateServiceImpl studentStateService;

    @GetMapping("test")
    public R test(){

        return R.ok().data("1",studentMessageStateService.getStateById("1")).data("2",studentStateService.getStateByID("1"));
    }
    @PostMapping("insertStu")
    public R insertStu(@RequestBody StudentMessage studentMessage){
        System.out.println("studentMessage ====================== " + studentMessage);
        if(studentService.insertStu(studentMessage)){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }

    @PostMapping("insertStuALL")
    public R insertStuALL(){

        return R.ok();
    }

    @GetMapping("bji/{id}")
    public R bji(@PathVariable("id") String id){
        StudentMessage studentMessage = studentService.selectStyById(id);
        return R.ok().data("stuMessage",studentMessage);
    }

    @PostMapping("tjiao")
    public R tjiao(@RequestBody StudentMessage studentMessage){
        System.out.println("studentMessage =============== " + studentMessage);
        int i = studentService.updateByStu(studentMessage);
        if(i==1){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

    @PostMapping("showbanzhang/{id}")
    public R showbanzhang(@PathVariable("id") String acc){
        Map<String,Object> map = studentService.selectPageBybanzhang(acc);
        return R.ok().data("instInfoList",map.get("pageList")).data("total",map.get("total"));
    }

    @PostMapping("showins/{id}")
    public R showins(@PathVariable("id") String acc,@RequestBody StuVO stuVO){
        Map<String,Object> map = studentService.selectPageByins(acc,stuVO);
        return R.ok().data("instInfoList",map.get("pageList")).data("total",map.get("total"));
    }
//
//    @DeleteMapping("deleteColl/{id}")
//    public R deleteColl(@PathVariable("id") String id){
//
//        if(studentService.deleteColl(id)){
//            return R.ok().data("mess","success");
//        }
//        return R.ok().data("mess","error");
//    }
//
//    @GetMapping("chakanByCollId/{id}")
//    public R chakanByCollId(@PathVariable("id") String id){
//        Long ID = Long.decode(id);
//        CollegeAdmin insInfoVO = studentService.getCollInfo(ID);
//        return R.ok().data("insInfoVO",insInfoVO);
//    }
//
//    @PostMapping("bianjiColl")
//    public R bianjiColl(@RequestBody CollegeAdmin instructor){
//
//        boolean b = studentService.updateColl(instructor);
//        if (b){
//            return R.ok().data("mess","success");
//        }
//        return R.ok().data("mess","error");
//    }

}
