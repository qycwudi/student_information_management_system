package com.qyc.controller;

import com.bean.StudentMessage;
import com.qyc.entity.StudentMessageVO;
import com.qyc.service.ServiceTest;
import com.qyc.service.impl.ServiceTestImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qyc
 * @time 2020/11/15 - 13:55
 */
@RestController
public class ControllerTest {
    @Autowired
    private ServiceTestImpl serviceTest;
    @PostMapping("/insert")
    public R insert(StudentMessageVO s){
        serviceTest.insert(s);
        R r = R.ok();
        r.data("stu",s);
        return r;
    }

    @GetMapping("/show")
    public R show(){
        StudentMessageVO  studentMessageVO = serviceTest.show();
        return R.ok().data("stu",studentMessageVO);
    }
}
