package com.losgai.spzx.manager.controller;

import com.losgai.spzx.manager.service.FileUploadService;
import com.losgai.spzx.model.vo.common.Result;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/admin/system")
public class FileUploadController { //minio 文件上传接口
    @Autowired
    FileUploadService fileUploadService;
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) {
        //这里的file是element-plus的默认名，要改需要加@RequestParam参数
        //1.获取上传的文件
        //2.调用service的方法
        String url =fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }

}
