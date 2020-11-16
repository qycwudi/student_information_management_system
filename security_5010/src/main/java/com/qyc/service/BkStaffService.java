package com.qyc.service;

import com.qyc.bean.BkStaff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qyc.bean.User;
import com.qyc.bean.UserVo;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-25
 */
public interface BkStaffService extends IService<BkStaff> {

    String login(User per);
    String getPassword(String account);
    UserVo getInfo(String account);
}
