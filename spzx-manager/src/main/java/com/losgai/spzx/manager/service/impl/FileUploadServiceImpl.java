package com.losgai.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.losgai.spzx.common.exception.SpzxException;
import com.losgai.spzx.manager.properties.MinioProperties;
import com.losgai.spzx.manager.service.FileUploadService;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) {
        try {
            // 创建MinioClient客户端对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpointUrl()) //自己minio服务器的访问地址
                            .credentials
                                    (minioProperties.getAccessKey(),
                                            minioProperties.getSecretKey())
                            .build();//用户名和密码
            // 判断是否有bucket，没有就创建
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .build());
            if (!found) {
                // 创建新 bucket 叫做 'spzx-bucket'.
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .build());
            } else {
                System.out.println("Bucket '" + minioProperties.getBucketName() + "' 已经存在。");
            }
            //1.获取上传的文件名称，让每个上传文件名称唯一 uuid生成
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //2.根据上传日期对上传文件分组 如20240510/uuid_xxx.png 并拼接字符串
            String fileName = dateDir + "/" + uuid + "_" + file.getOriginalFilename();

            //获取文件输入流
            InputStream fileInputStream = file.getInputStream();
            // 文件上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName()).
                            object(fileName)
                            .stream(fileInputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            //获取上传文件在minio的路径
            //http://192.168.200.132:9001/spzx-buket/2023-10-04_21.43.17.png
            String url = minioProperties.getEndpointUrl() + "/"
                    + minioProperties.getBucketName() + "/"
                    + fileName; //简单字符串拼接，但是文件名会重复
            System.out.println("返回了图片url： " + url);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
