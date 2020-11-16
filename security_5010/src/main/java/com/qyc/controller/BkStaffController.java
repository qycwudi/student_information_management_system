package com.qyc.controller;


import com.qyc.bean.User;
import com.qyc.bean.UserVo;
import com.qyc.cfg.JWTUtil;
import com.qyc.service.BkStaffService;
import com.util.R;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/sec/log")
@CrossOrigin
public class BkStaffController {
    @Autowired
    BkStaffService bkStaffService;

    @PostMapping("logins")
    public R login(@RequestBody Map<String, Object> map){
        System.out.println("user = " + map.toString());
        Object users = new Object();
        for (Map.Entry<String, Object> entry:map.entrySet()){
            users = entry.getValue();
        }
        JSONObject jsonObject=JSONObject.fromObject(users); // 将数据转成json字符串
        User per = (User) JSONObject.toBean(jsonObject, User.class); //将json转成需要的对象
        System.out.println("per = " + per);
        String token = bkStaffService.login(per);
        System.out.println("token = " + token);
        R r = R.ok();
        if(token.equals("")){
            return R.error();
        }
        r.data("token",token);
        return r;
    }
    @GetMapping("info")
    public R info(String token){
        System.out.println("info的token");
        JWTUtil jwtUtil = new JWTUtil();
        Map<String, Object> map = JWTUtil.verifyToken(token);
        String id = String.valueOf(map.get("id"));
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String roles = String.valueOf(map.get("roles"));
        String avatar = (String) map.get("avatar");
        System.out.println("map------info = " + map);
        R r = R.ok();
        List<String> role =  new ArrayList();
        role.add(roles);
        r.data("name",username).data("avatar","\n" + avatar).data("password",password).data("id",id).data("roles",role);
        return r;
    }
    @PostMapping("logout")
    public R loginout(){
        //清除token
        R r = R.ok();
        r.data("token","");
        return r;
    }

    @GetMapping("/testpass/{username}")
    public R testpass(@PathVariable("username") String username){
       UserVo userVo =  bkStaffService.getInfo(username);
        return R.ok().data("passwd",userVo);
    }
}

