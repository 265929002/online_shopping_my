package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import io.swagger.annotations.Api;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilenameFilter;

@RestController
@RequestMapping("admin/product/")
@Api(description = "文件上传")
public class FileUploadController {
//获取分布式系统文件url配置信息
    @Value("${fileServer.url}")
    private String fileServiceUrl;
    //    http://api.gmall.com/admin/product/fileUpload
//    file
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception{
/*
1. 加载配置文件tracker.conf
2. 初始化当前文件
3.创建TrackerClient
4.创建trackerServer
5.创建StorageClient1上传  StorageService存储
6.文件上传
 */
        String configFile = this.getClass().getResource("/tracker.conf").getFile();
        String path = "";
//        配置文件不为空，则初始化配置文件
            if (configFile!=null){
                ClientGlobal.init(configFile);
//                创建TrackerClient
                TrackerClient trackerClient = new TrackerClient();
//                创建trackerServer
                TrackerServer trackerServer = trackerClient.getConnection();
//                 创建StorageClient1
                StorageClient1 storageClient1 = new StorageClient1();
//                获取文件名后缀
                String extName = FilenameUtils.getExtension(file.getOriginalFilename());

//                文件上传
                 path = storageClient1.upload_appender_file1(file.getBytes(), extName, null);

                System.out.println("文件上传之后的路径: \t"+path);
            }
//返回最终的文件路径
        return Result.ok(fileServiceUrl+path);
    }
}
