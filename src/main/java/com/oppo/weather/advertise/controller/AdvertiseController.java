package com.oppo.weather.advertise.controller;


import com.oppo.weather.advertise.enums.Message;
import com.oppo.weather.advertise.model.Advertise;
import com.oppo.weather.advertise.service.AdvertiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Controller
@RequestMapping(path = "/advertise")
public class AdvertiseController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertiseController.class);

    @Autowired
    private AdvertiseService advertiseService;

    @Value("${spring.http.upload.img.dir}")
    private String imgDir;

    @Value("${spring.http.upload.img.suffix}")
    private String suffix;

    @Value("${spring.http.upload.img.height}")
    private int height;

    @Value("${spring.http.upload.img.width}")
    private int width;

    @RequestMapping(path = "index",method = RequestMethod.GET)
    public String advertiseIndex(){
        //返回广告上传页面
        return "advertise";
    }

    @RequestMapping(path = "upload",method=RequestMethod.POST)
    @ResponseBody
    public String uploadAd(Advertise advertise, @RequestParam("file") MultipartFile file){
        advertise.setAdIconName(saveImg(file));
        advertise.setLocationKey(getRandomString(20));
        advertise.setAdPos((short)3);
        advertise.setUpdateTime(new Date());
        boolean returnVal = advertiseService.uploadAd(advertise);
        return Message.search(returnVal);
    }

    /**
     * 保存上传图片到指定目录
     * @param file 图片文件
     */
    private String saveImg(MultipartFile file) {
        String imgName = UUID.randomUUID().toString();
        try {
            //todo 检查文件尺寸是否合规
            file.transferTo(new File(imgDir+imgName+suffix));
        } catch (IOException e) {
            //todo 异常处理策略
            e.printStackTrace();
        }
        return imgName;
    }


    private static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
