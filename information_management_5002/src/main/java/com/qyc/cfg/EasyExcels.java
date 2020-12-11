package com.qyc.cfg;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qyc.entity.StuExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qyc
 * @time 2020/11/23 - 22:28
 */
public class EasyExcels extends AnalysisEventListener<StuExcel> {

    private List<StuExcel> list = new ArrayList<>();

    @Override
    public void invoke(StuExcel condUser, AnalysisContext analysisContext) {
        list.add(condUser);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<StuExcel> read() {
        return list;
    }

}