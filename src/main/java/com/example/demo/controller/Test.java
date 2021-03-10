package com.example.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/sayHello")
    public String sayHello(String name, HttpServletRequest request){
        System.out.println("----------------"+request.getServerPort());
        return name;
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam(value = "head_img") MultipartFile file){
         String filePath = "D:\\ideawork\\demo\\src\\main\\resources\\static\\img\\";
        // 上传的文件名
        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));
        // 随机生成
        filename = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + filename);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
    @GetMapping("/getUserName")
    @HystrixCommand(fallbackMethod = "getNameFallBack")
    public String getName(String name){
        String userName = restTemplate.getForObject("http://user-service/user/getUserName/?name="+name,String.class);
        return  userName;
    }

    private String getNameFallBack(String name){
        return "调用服务出错";
    }
}
