package com.example.smalldemo.controller;

import com.example.smalldemo.pojo.Result;
import com.example.smalldemo.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String filename=file.getOriginalFilename();
        //保证文件名字唯一，防止文件覆盖
        filename= UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));
        //file.transferTo(new File("D:\\javacode\\Smalldemo\\src\\main\\resources\\image\\"+filename));
        String url=AliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(url);
    }
}
