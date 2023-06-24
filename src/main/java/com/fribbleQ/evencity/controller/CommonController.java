package com.fribbleQ.evencity.controller;

import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private AliOSSUtils aliOSSUtils;


    @PostMapping("/upload")
    public R<String> Upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        log.info("file:{}",inputStream);
        String upload = aliOSSUtils.upload(file);
        log.info("UUIDFilename:{}",upload);
        return R.success(upload);
    }

    // @GetMapping("/download")
    // public  InputStream getImageStream(String url,HttpServletResponse response) {
    //     try {
    //         HttpURLConnection connection = (HttpURLConnection) new URL("https://fribble-q.oss-cn-beijing.aliyuncs.com/11111e36-dea8-4d5d-9465-6f90d815460e.jpg").openConnection();
    //         connection.setReadTimeout(5000);
    //         connection.setConnectTimeout(5000);
    //         connection.setRequestMethod("GET");
    //         if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
    //             InputStream inputStream = connection.getInputStream();
    //             ServletOutputStream outputStream = response.getOutputStream();
    //             int i=0;
    //             byte[] bytes = new byte[1024];
    //         while ((i =inputStream.read(bytes))!=-1){
    //             outputStream.write(bytes,0,i);
    //             outputStream.flush();
    //         }
    //
    //             return inputStream;
    //         }
    //     } catch (IOException e) {
    //         log.info("获取网络图片出现异常，图片路径为：" + url);
    //         e.printStackTrace();
    //     }
    //     return null;
    // }


}
