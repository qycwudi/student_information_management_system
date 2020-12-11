package com.qyc.service;

import com.bean.ScholarshipInfo;
import com.bean.StudentMessage;
import com.qyc.entity.CascaderChild;
import com.qyc.entity.StuVO;

import java.util.List;

/**
 * @author qyc
 * @time 2020/11/25 - 15:20
 */
public interface ScholService {
    int updateSchState(boolean value);

    int addSch(String value, String name);

    boolean schJc(String id);

    List<ScholarshipInfo> selectJlByStuAcc(String acc);

    boolean collschJc();

    List<StudentMessage> showbanzhangList(String acc);

    int updateStateBYbanzhang(String id);

    List<StudentMessage> showInstList(String acc);

    List<StudentMessage> showInstListByVo(String acc, String state, String classID);

    int updateStateBYInsTG(String id);

    int updateStateBYInsJj(String id);

    List<StudentMessage> selectCollList(String state, String value, StuVO stuVO);

    boolean updateSchStateByCollTG(String id);

    boolean updateSchStateJJ(String id);
}
