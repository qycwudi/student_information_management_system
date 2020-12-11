package com.qyc.controller;

import com.qyc.bean.OSSProperties;
import com.qyc.cfg.OSSUtil;
import com.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qyc
 * @time 2020/7/2 - 22:57
 */
@RestController
@RequestMapping("/oss/add")
@CrossOrigin
public class OSSController {

    @Autowired
    private OSSProperties ossProperties;
    @PostMapping("/storage")
    public R storage(@RequestPart(value = "file")MultipartFile file) throws IOException {
        System.out.println("进入上传");
        boolean stat = OSSUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                file.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                file.getOriginalFilename());
        R r = R.ok();
        r.data("url", OSSUtil.getPath());
        System.out.println(OSSUtil.getPath());
        return r;
    }
}
