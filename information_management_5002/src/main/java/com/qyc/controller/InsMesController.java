package com.qyc.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.qyc.entity.CascaderParent;
import com.qyc.entity.InsInfoVO;
import com.qyc.entity.InsVo;
import com.qyc.service.impl.CollServiceImpl;
import com.qyc.service.impl.InsServiceImpl;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/16 - 19:29
 */
@RestController
@CrossOrigin
@RequestMapping("/ins")
public class InsMesController {
    @Autowired
    private InsServiceImpl insServiceImpl;

    //辅导员分页+三条件模糊查询
    @PostMapping("showInsPage/{num}/{size}/{value}")
    public R showPage(@PathVariable("num") int num, @PathVariable("size") int size,@PathVariable("value") String value, @RequestBody InsVo insVo){
        if (!value.equals("undefined")){
            insVo.setInsClass(value);
        }
        System.out.println("insVo ==************** " + insVo);
        Map<Object,Object> map = insServiceImpl.selectPage(num,size,insVo);
        Page<Instructor> page = (Page<Instructor>) map.get("page");
        System.out.println("page = " + page);
        Map<String,Integer> list = (Map<String, Integer>) map.get("list");

        return R.ok().data("instInfoList",page).data("num",page.getCurrent()).data("size",page.getSize()).data("total",page.getTotal())
                .data("classNumMap",list);
    }

    //添加辅导员 姓名+电话+办公地址
    @PostMapping("insertIns")
    public R insertIns(@RequestBody Instructor instructor){
        System.out.println("instructor = " + instructor);
        if(insServiceImpl.insertIns(instructor)){
            return R.ok().data("mess","success");
        }else {
            return R.ok().data("mess","error");
        }
    }

    //查找班级级联选择器
    @GetMapping("cascader")
    public R cascader(){

        List<CascaderParent> list = insServiceImpl.getCascader();
        if (list.size()==0){
            return R.ok().data("casVal",null);
        }else {
            return R.ok().data("casVal",list);
        }
    }

    @GetMapping("chakanByInsId/{id}")
    public R chakanByInsId(@PathVariable("id") String id){
        Long ID = Long.decode(id);
        InsInfoVO insInfoVO = insServiceImpl.getInsInfo(ID);
        return R.ok().data("insInfoVO",insInfoVO);
    }

    @PostMapping("bianjiIns")
    public R bianjiIns(@RequestBody Instructor instructor){
        System.out.println("instructor = " + instructor);
        boolean b = insServiceImpl.updateIns(instructor);
        if (b){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

    @DeleteMapping("deleteInst/{id}")
    public R deleteInst(@PathVariable("id") String id){

        if(insServiceImpl.deleteInstById(id)){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

   //------------------*******管理员********-----------------------
    @Autowired
    private CollServiceImpl collService;

    @PostMapping("showCollPage/{num}/{size}/{value}")
    public R showCollPage(@PathVariable("num") int num, @PathVariable("size") int size,@PathVariable("value") String value, @RequestBody InsVo insVo){
        if (!value.equals("undefined")){
            insVo.setInsClass(value);
        }
        Map<Object,Object> map = collService.selectPage(num,size,insVo);
        Page<Instructor> page = (Page<Instructor>) map.get("page");
        return R.ok().data("instInfoList",page).data("num",page.getCurrent()).data("size",page.getSize()).data("total",page.getTotal());
    }
   @PostMapping("insertColl")
   public R insertColl(@RequestBody CollegeAdmin admin){
       if(collService.insertColl(admin)){
           return R.ok().data("mess","success");
       }else {
           return R.ok().data("mess","error");
       }
   }

    @DeleteMapping("deleteColl/{id}")
    public R deleteColl(@PathVariable("id") String id){

        if(collService.deleteColl(id)){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

    @GetMapping("chakanByCollId/{id}")
    public R chakanByCollId(@PathVariable("id") String id){
        Long ID = Long.decode(id);
        CollegeAdmin insInfoVO = collService.getCollInfo(ID);
        return R.ok().data("insInfoVO",insInfoVO);
    }

    @PostMapping("bianjiColl")
    public R bianjiColl(@RequestBody CollegeAdmin instructor){

        boolean b = collService.updateColl(instructor);
        if (b){
            return R.ok().data("mess","success");
        }
        return R.ok().data("mess","error");
    }

}
