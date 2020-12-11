package com.qyc.controller;

import com.bean.StudentMessage;
import com.qyc.entity.StudentMessageVO;
import com.qyc.service.impl.ServiceTestImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qyc
 * @time 2020/11/15 - 13:55
 */
@RestController
public class ControllerTest {
    @Autowired
    private ServiceTestImpl serviceTestImpl;
    @PostMapping("/insert")
    public R insert(StudentMessageVO s){
        StudentMessage studentMessage = new StudentMessage();
        studentMessage.setStuName(s.getStuName());
        serviceTestImpl.insert(studentMessage);
        R r = R.ok();
        r.data("stu",s);
        return r;
    }

    @GetMapping("/show")
    public R show(){
        StudentMessageVO  studentMessageVO = serviceTestImpl.show();
        return R.ok().data("stu",studentMessageVO);
    }
}
