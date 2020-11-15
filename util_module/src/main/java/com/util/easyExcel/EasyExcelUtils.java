package com.util.easyExcel;

import com.alibaba.excel.EasyExcel;

import java.util.List;

/**
 * @author qyc
 * @time 2020/7/3 - 5:37
 */
public class EasyExcelUtils {
    public static boolean  write(String fileName,String sheet, List<Object> message){

        try{
            EasyExcel.write(fileName,Object.class).sheet(sheet).doWrite(message);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
