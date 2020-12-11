package com.qyc.service;

import com.qyc.cfg.FeignSupportConfig;
import com.util.R;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * @author qyc
 * @time 2020/11/28 - 0:11
 */
//@Component
@Repository
@FeignClient(value = "oss-5011",configuration = FeignSupportConfig.class)

public interface OSS5011 {
    @PostMapping("oss/add/storage")
    public R storage(@RequestPart(value = "file")MultipartFile file)throws IOException ;
}
