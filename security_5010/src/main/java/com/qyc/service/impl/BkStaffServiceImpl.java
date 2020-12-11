package com.qyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bean.CollegeAdmin;
import com.bean.Instructor;
import com.bean.StudentMessage;
import com.qyc.bean.BkStaff;
import com.qyc.bean.User;
import com.qyc.bean.UserVo;
import com.qyc.cfg.JWTUtil;
import com.qyc.mapper.BkStaffMapper;
import com.qyc.mapper.PasswordMapper;
import com.qyc.service.BkStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.util.JiaMi;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-25
 */
@Service
public class BkStaffServiceImpl extends ServiceImpl<PasswordMapper, BkStaff> implements BkStaffService {

    @Override
    public String login(User user) {
        JWTUtil jwtUtil = new JWTUtil();
        UserVo userVo = getInfo(user.getUsername());
        System.out.println("userVo = " + userVo);
        if (userVo != null) {
            String Upasswd = JiaMi.jiami(user.getPassword());

            if (Upasswd.equals(userVo.getPassword())) {
                System.out.println("进入");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", userVo.getID());
                hashMap.put("username", userVo.getAccount());
                if(userVo.getImgUrl()==null||userVo.getImgUrl().equals("")){
                    hashMap.put("avatar", "");
                }else {
                    hashMap.put("avatar", userVo.getImgUrl());
                }

                hashMap.put("password", userVo.getPassword());
                if(userVo.getRole().equals("1")){
                    hashMap.put("roles", "admin");
                }else if(userVo.getRole().equals("2")){
                    hashMap.put("roles", "instr");
                }else if(userVo.getRole().equals("3")){
                    hashMap.put("roles", "stude");
                }else if(userVo.getRole().equals("4")){
                    hashMap.put("roles", "banzhang");
                }else if(userVo.getRole().equals("0")){
                    hashMap.put("roles","coll");
                }
                System.out.println("hashMap = " + hashMap.toString());
                String token = jwtUtil.createToken(hashMap);
                return token;
            } else {
                return "";
            }
        }
        return "";

    }

    public String getPassword(String account){
        String passwd = "";
        if(account.length()==6){
            passwd = baseMapper.getcoll(account);
        }
        else if(account.length()==11){
            passwd = baseMapper.getins(account);
        }
        else if(account.length()==10){
            passwd = baseMapper.getstu(account);
        }
        return passwd;
    }

    @Override
    public UserVo getInfo(String account) {
        String passwd = "";
        UserVo userVo = new UserVo();
        if(account.length()==6){
            CollegeAdmin coll = baseMapper.getColl(account);
            userVo.setID(coll.getId());
            userVo.setAccount(coll.getAccount());
            userVo.setPassword(coll.getCollAdmPassword());
            userVo.setRole(coll.getRole());
            userVo.setImgUrl("");
        }
        else if(account.length()==11){
            Instructor ins = baseMapper.getIns(account);
            userVo.setID(ins.getId());
            userVo.setAccount(ins.getAccount());
            userVo.setPassword(ins.getInsPassword());
            userVo.setRole(ins.getRole());
            userVo.setImgUrl(ins.getImgUrl());
        }
        else if(account.length()==10){

            StudentMessage stu = baseMapper.getStu(account);
            userVo.setID(stu.getId());
            userVo.setAccount(stu.getAccount());
            userVo.setPassword(stu.getStuPassword());
            userVo.setRole(stu.getRole());
            userVo.setImgUrl(stu.getStuImgUrl());
        }
        return userVo;
    }
}
