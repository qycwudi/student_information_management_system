package com.qyc.cfg;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author qyc
 * @time 2020/7/2 - 23:06
 */
public class OSSUtil {
    private static String path ;

    public static String getPath() {
        return path;
    }

    public static boolean uploadFileToOss(
            String endpoint, String accessKeyId, String accessKeySecret,
            InputStream inputStream, String bucketName, String bucketDomain, String originalName) {


        OSS ossclent = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        String objectName ="stuSys/"+ folderName + "/" + fileMainName + extensionName;

        try {
            PutObjectResult putObjectResult = ossclent.putObject(bucketName, objectName, inputStream);
            ResponseMessage responseMessage = putObjectResult.getResponse();
            if (responseMessage == null) {
                //资源路径
                //      https://qyc-oostest.oss-cn-beijing.aliyuncs.com/20200624/2b9f74b42a6c4f7f87d846fa4a3cdfad.png
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                path = ossFileAccessPath;
                return true;
            } else {
                int statusCode = responseMessage.getStatusCode();
                String errorMessage = responseMessage.getErrorResponseAsString();
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (ossclent != null) {
                ossclent.shutdown();
            }
        }


    }
}

