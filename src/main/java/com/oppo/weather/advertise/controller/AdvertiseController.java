package com.oppo.weather.advertise.controller;


import com.oppo.weather.advertise.config.ImageProperties;
import com.oppo.weather.advertise.config.ResponseResult;
import com.oppo.weather.advertise.model.Advertise;
import com.oppo.weather.advertise.service.AdvertiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


@RestController
@RequestMapping(path = "/advertise")
public class AdvertiseController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertiseController.class);

    @Autowired
    private AdvertiseService advertiseService;

    @Autowired
    private ImageProperties imageProperties;

    @RequestMapping(path = "index",method = RequestMethod.GET)
    public String advertiseIndex(){ //返回广告上传页面
        return "advertise";
    }

    @RequestMapping(path = "upload",method=RequestMethod.POST)
    public ResponseResult uploadAd(Advertise advertise, @RequestParam("file") MultipartFile file){
        String imgName = saveImg(file);
        if(Objects.isNull(imgName)){
            return new ResponseResult("000","上传失败");
        }else{
            advertise.setAdIconName(imgName);
            advertise.setLocationKey(getRandomString(20));
            advertise.setAdPos((short)3);
            advertise.setUpdateTime(new Date());
            boolean returnVal = advertiseService.uploadAd(advertise);
            return new ResponseResult("001","上传成功");
        }
    }

    /**
     * 保存上传图片到指定目录
     * @param file 图片文件
     * @return 保存成功后,随机生成的文件名
     */
    private String saveImg(MultipartFile file) {
        String imgName=null;
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            int height = originalImage.getHeight();
            int width = originalImage.getWidth();
            if(height == imageProperties.getHeight() && width == imageProperties.getWidth()){//检查文件尺寸是否合规
                imgName = UUID.randomUUID().toString();
                file.transferTo(new File(imageProperties.getImgDir() + imgName + imageProperties.getSuffix()));
            }else{
                logger.warn("图片尺寸不合格,请上传height={},width={}规格的图片",imageProperties.getHeight(),imageProperties.getWidth());
            }
        } catch (IOException e) {
            logger.error("图片上传异常,e={}",e.getMessage());
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
