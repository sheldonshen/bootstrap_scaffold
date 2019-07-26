package com.oppo.weather.advertise.controller;


import com.oppo.weather.advertise.config.ImageProperties;
import com.oppo.weather.advertise.config.ResponseResult;
import com.oppo.weather.advertise.model.Advertise;
import com.oppo.weather.advertise.service.AdvertiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


@Controller
@RequestMapping(path = "/advertise")
public class AdvertiseController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertiseController.class);

    @Autowired
    private AdvertiseService advertiseService;

    @Autowired
    private ImageProperties imageProperties;

    private  String message;

    @RequestMapping(path = "index",method = RequestMethod.GET)
    public String advertiseIndex(){ //返回广告上传页面
        return "advertise";
    }

    @RequestMapping(path = "upload",method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult uploadAd(Advertise advertise, @RequestParam("file") MultipartFile file){
        ResponseResult responseResult;
        message = null;
        String imgName = saveImg(file);
        if(Objects.isNull(imgName)){
            responseResult = new ResponseResult("002",message);
        }else{
            advertise.setAdIconName(imgName);
            advertise.setLocationKey(getRandomString(20));
            advertise.setAdPos((short)3);
            advertise.setUpdateTime(new Date());
            boolean returnVal = advertiseService.uploadAd(advertise);
            if(returnVal){
                responseResult = new ResponseResult("000","上传成功");
            }
            responseResult =  new ResponseResult("001","上传失败");
        }
        return responseResult;
    }

    /**
     * 保存上传图片到指定目录
     * @param file 图片文件
     * @return 保存成功后,返回随机生成的文件名
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
                message = "图片尺寸不合格,请上传height="+imageProperties.getHeight()+",width="+imageProperties.getWidth()+"规格的图片";
                logger.warn("图片尺寸不合格,请上传height={},width={}规格的图片",imageProperties.getHeight(),imageProperties.getWidth());
            }
        } catch (IOException e) {
            message = "图片上传异常";
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
