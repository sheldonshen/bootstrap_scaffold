package com.oppo.weather.advertise.controller;


import com.oppo.weather.advertise.config.ImageProperties;
import com.oppo.weather.advertise.common.ResponseResult;
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

    //消除全局变量为(方法)局部变量,减小变量范围
    //private  String message;

    @RequestMapping(path = "index", method = RequestMethod.GET)
    public String advertiseIndex() { //返回广告上传页面
        return "advertise";
    }

    @RequestMapping(path = "upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult uploadAd(Advertise advertise, @RequestParam("file") MultipartFile file) {
        ResponseResult responseResult;

        try {
            String  errorMessage   = validateImageProperties(file);//一个变量,承担了2层含义(意思):拆解成2个变量,每个变量承担一个含义
            boolean isValidatePass = Objects.isNull(errorMessage);//表达式可读性

            if (isValidatePass) {
                String imgName = generateImgName();
                saveImgFileToDir(file, imgName);
                String saveMessage = saveAdvertiseToDB(advertise, imgName);
                responseResult = new ResponseResult("图片上次成功", saveMessage);//枚举可读性
            } else {
                responseResult = new ResponseResult("图片校验失败", errorMessage);
            }

        } catch (IOException e) {//todo 通用异常处理
            String exceptionMesage = e.getMessage();
            logger.error("上传图片异常,e={}", exceptionMesage);
            responseResult = new ResponseResult("上传图片异常", exceptionMesage);
        }
        return responseResult;
    }

    private String validateImageProperties(MultipartFile file) throws IOException {
        String message = null;

        boolean isImage = file.getContentType().contains("image");//检查文件是否是图片类型

        //将多层if,else语句拆解成单层的if语句
        if (!isImage) {
            logger.warn("请上传图片文件");
            message = "请上传图片文件";
            return message;
        }

        //将功能相关联性紧密的代码分成不同的代码【段落】
        int           height              = imageProperties.getHeight();
        int           width               = imageProperties.getWidth();
        BufferedImage originalImage       = ImageIO.read(file.getInputStream());
        int           originalImageHeight = originalImage.getHeight();
        int           originalImageWidth  = originalImage.getWidth();

        if (height != originalImageHeight || width != originalImageWidth) { //检查图片尺寸是否合规
            logger.warn("图片尺寸不合格,请上传height={},width={}规格的图片", height, width);
            message = "图片尺寸不合格,请上传height=" + height + ",width=" + width + "规格的图片";
            return message;
        }

        return message;
    }

    private boolean createImgDir() {
        File dir = new File(imageProperties.getImgDir());

        if (!dir.exists()) {
            return dir.mkdirs();
        } else {
            return true;
        }
    }

    private String generateImgName() {
        String imgName = UUID.randomUUID().toString();
        return imgName;
    }


    /**
     * 保存上传图片到指定目录
     *
     * @param file    图片文件
     * @param imgName 新生成的图片文件名称
     */
    private void saveImgFileToDir(MultipartFile file, String imgName) throws IOException {
        if (createImgDir()) {
            file.transferTo(new File(imageProperties.getImgDir() + imgName + imageProperties.getSuffix()));
        }
    }

    /**
     * 保存广告到数据库
     *
     * @param advertise 图片广告
     * @param imgName   新生成的图片文件名称
     * @return
     */
    private String saveAdvertiseToDB(Advertise advertise, String imgName) {
        advertise.setAdIconName(imgName);
        advertise.setLocationKey(getRandomString(20));
        advertise.setAdPos((short) 3);
        advertise.setUpdateTime(new Date());

        boolean returnVal = advertiseService.uploadAd(advertise);

        String message;
        if (returnVal) {
            message = "保存图片名称成功";
        } else {
            message = "保存图片名称失败";
        }
        return message;
    }

    private static String getRandomString(int length) {
        String       str    = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random       random = new Random();
        StringBuffer sb     = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
