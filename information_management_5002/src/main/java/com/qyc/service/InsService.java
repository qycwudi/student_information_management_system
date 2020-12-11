package com.qyc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.Instructor;
import com.qyc.entity.CascaderParent;
import com.qyc.entity.Info_classVo;
import com.qyc.entity.InsInfoVO;
import com.qyc.entity.InsVo;

import java.util.List;
import java.util.Map;

/**
 * @author qyc
 * @time 2020/11/16 - 19:35
 */
public interface InsService {
    //辅导员 分页+模糊查询name、phone、classNum
    Map<Object,Object> selectPage(int num, int size, InsVo insVo);

    //添加辅导员
    boolean insertIns(Instructor instructor);

    //查找所有年级+班级级联选择器需要的value
    List<CascaderParent> getCascader();

    InsInfoVO getInsInfo(Long id);

    boolean updateIns(Instructor instructor);

    boolean deleteInstById(String id);

    List<Info_classVo> selectInsName();

    String selectInsNameByID(Long classInsId);

    String selectIDByAcc(String acc);

    Instructor selectInsInfo(String id);

    int updateInsByInsId(Instructor instructor);
}
