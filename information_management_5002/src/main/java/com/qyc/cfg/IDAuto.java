package com.qyc.cfg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qyc
 * @time 2020/11/15 - 20:34
 */
//@Configuration
public class IDAuto {
    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy").format(new Date()));
    }
}


