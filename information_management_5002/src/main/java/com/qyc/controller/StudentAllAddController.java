package com.qyc.controller;

import com.alibaba.excel.EasyExcel;
import com.qyc.cfg.EasyExcels;
import com.qyc.entity.StuExcel;
import com.qyc.service.impl.StudentMessageStateServiceImpl;
import com.qyc.service.impl.StudentServiceImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qyc
 * @time 2020/11/23 - 22:07
 */
@RestController
@RequestMapping("/ins/add")
@CrossOrigin
public class StudentAllAddController {

    @Autowired
    StudentServiceImpl studentService;
    @PostMapping("/all")
    public R storage(MultipartFile file) throws IOException {
        System.out.println("进入上传"+file.getName());
        R r = R.ok();
        EasyExcels easyExcel = new EasyExcels();
        EasyExcel.read(file.getInputStream(),StuExcel.class,easyExcel).sheet().doRead();
        try {
            boolean i = studentService.addAll(easyExcel.read());
            if (i){
                r.data("mess", "success");
            }else {
                r.data("mess","error");
            }
        }catch (Exception e){
            r.data("mess","error");
        }

//        finally {
//            System.out.println("yb = " + yb+"   read.size()"+read.size());
//            if(yb==read.size()){
//                return true;
//            }else {
//                return false;
//            }
//        }
        return r;
    }
}