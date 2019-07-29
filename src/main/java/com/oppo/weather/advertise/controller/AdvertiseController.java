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
        try {
            if(Objects.isNull(validateImageProperties(file))){//1
                String imgName = generateImgName();//2
                saveImgFileToDir(file,imgName);//3
                message = saveImgNameToDB(advertise,imgName);//4
                responseResult = new ResponseResult("003",message);
            }else{
                responseResult = new ResponseResult("001",message);
            }
        } catch (IOException e) {
            logger.error("上传图片异常,e={}",e.getMessage());
            responseResult = new ResponseResult("002","上传图片异常");
        }
        return responseResult;
    }

    private String validateImageProperties(MultipartFile file) throws IOException{
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();
        if(file.getContentType().contains("image")){//检查文件是否是图片类型
            logger.warn("请上传图片文件");
            message = "请上传图片文件";
        }else if(height != imageProperties.getHeight() ||  width != imageProperties.getWidth()){ //检查图片尺寸是否合规
            logger.warn("图片尺寸不合格,请上传height={},width={}规格的图片",imageProperties.getHeight(),imageProperties.getWidth());
            message = "图片尺寸不合格,请上传height="+imageProperties.getHeight()+",width="+imageProperties.getWidth()+"规格的图片";
        }
        return message;
    }

    private String generateImgName(){
        String imgName = UUID.randomUUID().toString();
        return imgName;
    }

    private boolean createImgDir(){
        File dir = new File(imageProperties.getImgDir());
        if(!dir.exists()){
            return dir.mkdir();
        }else{
            return true;
        }
    }

    /**
     * 保存上传图片到指定目录
     * @param file 图片文件
     * @return 保存成功后,返回随机生成的文件名
     */
    private void saveImgFileToDir(MultipartFile file,String imgName) throws IOException {
        if(createImgDir()){
            //
            file.transferTo(new File(imageProperties.getImgDir() + imgName + imageProperties.getSuffix()));
        }
    }

    private String saveImgNameToDB(Advertise advertise,String imgName){
        advertise.setAdIconName(imgName);
        advertise.setLocationKey(getRandomString(20));
        advertise.setAdPos((short)3);
        advertise.setUpdateTime(new Date());
        boolean returnVal = advertiseService.uploadAd(advertise);
        if(returnVal){
            message = "保存图片名称成功";
        }else{
            message = "保存图片名称失败";
        }
        return message;
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
