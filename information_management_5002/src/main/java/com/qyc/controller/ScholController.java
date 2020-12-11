package com.qyc.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.bean.ClassInfo;
import com.bean.ScholarshipInfo;
import com.bean.StudentMessage;
import com.qyc.entity.CascaderChild;
import com.qyc.entity.CascaderParent;
import com.qyc.entity.SchExcelVo;
import com.qyc.entity.StuVO;
import com.qyc.service.OSS5011;
import com.qyc.service.impl.ClassServiceImpl;
import com.qyc.service.impl.InsServiceImpl;
import com.qyc.service.impl.ScholServiceImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/25 - 14:59
 */
@RestController
@CrossOrigin
@RequestMapping("/ins")
public class ScholController {
    @Autowired
    private ScholServiceImpl scholService;

//    @Autowired
//    private OSS5011 oss5011;
//
//    @PostMapping("storage")
//    public R storage(MultipartFile file) throws IOException {
//        oss5011.storage(file);
//        return R.ok();
//    }
    //修改奖学金申请状态
    @GetMapping("schol/bjSchSta/{value}")
    public R bjSchSta(@PathVariable("value") boolean value ){
        int i = scholService.updateSchState(value);
        if(i==1){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }

    }
    @GetMapping("schol/stuTjSch/{value}/{name}")
    public R stuTjSch(@PathVariable("value") String value,@PathVariable("name") String name){

        int i = scholService.addSch(value,name);
        if(i==1){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }
    @GetMapping("schol/jcStuSch/{id}")
    public R jcStuSch(@PathVariable("id") String id){
        System.out.println("idjc = " + id);
        boolean i = scholService.schJc(id);
        return R.ok().data("qx",i);
    }
    @GetMapping("schol/jlStuSch/{id}")
    public R jlStuSch(@PathVariable("id") String acc){
        List<ScholarshipInfo> list = scholService.selectJlByStuAcc(acc);
        return R.ok().data("jl",list);
    }
    @GetMapping("schol/ColljcStuSch")
    public R ColljcStuSch(){
        boolean i = scholService.collschJc();
        return R.ok().data("qx",i);
    }

    @GetMapping("schol/ShowbanzhangList/{acc}")
    public R ShowbanzhangList(@PathVariable("acc") String acc){
        List<StudentMessage> list = scholService.showbanzhangList(acc);
        return R.ok().data("banzhanglist",list).data("total",list.size());
    }

    @GetMapping("bztongguo/{id}")
    public R bztongguo(@PathVariable("id") String id){
        int i = scholService.updateStateBYbanzhang(id);
        if(i==1){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }

    @GetMapping("schol/ShowInstList/{acc}")
    public R ShowInstList(@PathVariable("acc") String acc){
        List<StudentMessage> list = scholService.showInstList(acc);
        return R.ok().data("instVOList",list).data("total",list.size());
    }

    @Autowired
    private ClassServiceImpl classService;

    @Autowired
    private InsServiceImpl insService;
    @GetMapping("schol/selectClassByInsAccount/{acc}")
    public R selectClassByInsAccount(@PathVariable("acc") String acc){
        String insId = insService.selectIDByAcc(acc);
        List<ClassInfo> list = classService.selectClassListByInstId(new Long(insId));
        List<CascaderChild> list1 = new ArrayList<>();
        for (ClassInfo c:
                list) {
            CascaderChild cascaderChild = new CascaderChild();
            cascaderChild.setLabel(c.getClassName());
            cascaderChild.setValue(c.getId().toString());
            list1.add(cascaderChild);
        }
        return R.ok().data("InsClassList",list1);
    }

    @GetMapping("schol/ShowInstListByVo/{acc}/{state}/{classId}")
    public R ShowInstListByVo(@PathVariable("acc") String acc,@PathVariable("state") String state,@PathVariable("classId") String classId){

        System.out.println("acc = " + acc);
        System.out.println("state = " + state);
        System.out.println("classID = " + classId);
        List<StudentMessage> list = scholService.showInstListByVo(acc,state,classId);
        return R.ok().data("instVOList",list).data("total",list.size());
    }
    @GetMapping("schol/insttongguo/{id}")
    public R insttongguo(@PathVariable("id") String id){
        int i = scholService.updateStateBYInsTG(id);
        if(i==1){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }
    @GetMapping("schol/instjjue/{id}")
    public R instjjue(@PathVariable("id") String id){
        int i = scholService.updateStateBYInsJj(id);
        if(i==1){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }

    @PostMapping("schol/excel")
    public R excel(@RequestBody List<StudentMessage> list, HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<SchExcelVo> list1 = new ArrayList<>();
        for (StudentMessage s :
                list) {
            SchExcelVo schExcelVo = new SchExcelVo();
            schExcelVo.setName(s.getStuName());;
            schExcelVo.setAcc(s.getAccount());;
            schExcelVo.setSchType(s.getStuIdCard());
            list1.add(schExcelVo);
        }
        String fileName = "奖学金名单";
        String sheetName = "名单";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        double rand = Math.random()*10;
//        String string = new String(rand)
//        String filename = new SimpleDateFormat("hh:mm:ss").format(new Date())+;
        EasyExcel.write(response.getOutputStream()).sheet(sheetName).doWrite(list1);
        System.out.println("list1 = " + list1);

        return R.ok().data("files",response.getOutputStream());
    }


    @PostMapping("schol/ShowCollListByVo/{id}/{state}/{value}")
    public R ShowCollListByVo(@PathVariable("id") String id, @PathVariable("state") String state,
                              @PathVariable("value") String value, @RequestBody StuVO stuVO){
        System.out.println("id = " + id);
        System.out.println("state = " + state);
        System.out.println("value = " + value);
        System.out.println("stuVO = " + stuVO);
        List<StudentMessage> list = scholService.selectCollList(state,value,stuVO);
        System.out.println("list ============ " + list);
        return R.ok().data("collVOList",list).data("total",list.size());
    }

    @GetMapping("schol/colltongguo/{id}")
    public R colltongguo(@PathVariable("id") String id){
        boolean  i = scholService.updateSchStateByCollTG(id);
    if(i){
        return R.ok().data("mess","success");
    }
        return R.ok().data("mess","error");
    }

    @GetMapping("schol/colljjue/{id}")
    public R colljjue(@PathVariable("id") String id){

        boolean i  = scholService.updateSchStateJJ(id);
        if(i){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

}
