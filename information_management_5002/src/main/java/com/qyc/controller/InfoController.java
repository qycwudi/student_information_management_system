package com.qyc.controller;

import com.bean.ClassInfo;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.bean.StudentMessage;
import com.qyc.entity.InsInfoVO;
import com.qyc.entity.InsVo;
import com.qyc.entity.StudentMessageVO;
import com.qyc.service.ClassService;
import com.qyc.service.impl.ClassServiceImpl;
import com.qyc.service.impl.CollServiceImpl;
import com.qyc.service.impl.InsServiceImpl;
import com.qyc.service.impl.StudentServiceImpl;
import com.util.JiaMi;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qyc
 * @time 2020/11/29 - 2:05
 */
@RestController
@CrossOrigin
@RequestMapping("ins/info")
public class InfoController {


    @Autowired
    private CollServiceImpl collService;
    @GetMapping("selectAdmInfoById/{id}")
    public R selectAdmInfoById(@PathVariable("id") String id){

        CollegeAdmin collegeAdmin = collService.selectAdminInfo(id);
        return R.ok().data("info",collegeAdmin);
    }
    @GetMapping("selectCollInfoById/{id}")
    public R selectCollInfoById(@PathVariable("id") String id){

        CollegeAdmin collegeAdmin = collService.selectCollInfo(id);
        return R.ok().data("info",collegeAdmin);
    }

    @Autowired
    private InsServiceImpl insService;
    @Autowired
    private ClassServiceImpl classService;
    @GetMapping("selectInstInfoById/{id}")
    public R selectInstInfoById(@PathVariable("id") String id){
        Instructor instructor = insService.selectInsInfo(id);
        List<ClassInfo> classList = classService.selectClassListByInstId(new Long(id));
        List<String> list = new ArrayList<>();
        for (ClassInfo c :
                classList) {
            list.add(c.getClassName());
        }
        return R.ok().data("info",instructor).data("classList",list);
    }

    @Autowired
    private StudentServiceImpl studentService;
    @GetMapping("selectStuInfoById/{id}")
    public R selectStuInfoById(@PathVariable("id") String id){

        StudentMessageVO studentMessage = studentService.selectStuInfo(id);
        return R.ok().data("info",studentMessage);
    }

    @PostMapping("updateCollAdmin")
    public R updateCollAdmin(@RequestBody CollegeAdmin collegeAdmin){
        if(collService.updateCollById(collegeAdmin)){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }
    @GetMapping("updatePassWord/{old}/{input}")
    public R updatePassWord(@PathVariable("old") String old,@PathVariable("input") String input){

        String oldpasswd = JiaMi.jiami(input);
        if(old.equals(oldpasswd)){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }
    @GetMapping("updateInstPassWord/{old}/{input}")
    public R updateInstPassWord(@PathVariable("old") String old,@PathVariable("input") String input){
        String oldpasswd = JiaMi.jiami(input);
        if(old.equals(oldpasswd)){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }
    @PostMapping("updateInst")
    public R updateInst(@RequestBody Instructor instructor){

        int i = insService.updateInsByInsId(instructor);
        if(i==1){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

    @PostMapping("updateStu")
    public R updateStu(@RequestBody StudentMessageVO studentMessageVO){

        System.out.println("studentMessageVO = ===========**************" + studentMessageVO);
        int i = studentService.updateStuByPhone(studentMessageVO);
        if(i==1){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }
}
