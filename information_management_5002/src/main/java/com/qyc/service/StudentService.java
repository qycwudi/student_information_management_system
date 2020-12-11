package com.qyc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bean.StudentMessage;
import com.qyc.entity.InsVo;
import com.qyc.entity.StuExcel;
import com.qyc.entity.StuVO;
import com.qyc.entity.StudentMessageVO;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/19 - 20:34
 */
public interface StudentService {
    boolean insertStu(StudentMessage studentMessage);

    Map<String, Object> selectPage(StuVO insVo);

    boolean addAll(List<StuExcel> read);

    StudentMessage selectStuByAcc(String name);

    void updateInsIdByClassId(Long classId, Long insId);

    StudentMessage selectStyById(String id);

    int updateByStu(StudentMessage studentMessage);

    List<String> selectStuClassIdByInsId(String acc);

    StudentMessage selectByWrapper(QueryWrapper wrapper1);

    StudentMessage selectStyByIdAndStuVo(String schStuId, StuVO stuVO);

    Map<String, Object> selectPageBybanzhang(String acc);

    Map<String, Object> selectPageByins(String acc, StuVO stuVO);

    StudentMessageVO selectStuInfo(String id);

    int updateStuByPhone(StudentMessageVO studentMessageVO);
}
