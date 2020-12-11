package com.qyc.bean;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qyc
 * @time 2020/7/2 - 23:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
//读取配置文件的内容
@ConfigurationProperties(prefix = "aliyun.oss")
public class OSSProperties {
    private String endPoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketDomain;
}
