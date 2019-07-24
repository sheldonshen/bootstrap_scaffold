package com.oppo.weather.advertise.controller;


import com.oppo.weather.advertise.enums.Message;
import com.oppo.weather.advertise.model.Advertise;
import com.oppo.weather.advertise.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Random;


@Controller
@RequestMapping(path = "/advertise")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    @RequestMapping(path = "index",method = RequestMethod.GET)
    public String advertiseIndex(){
        //返回广告上传页面
        return "advertise";
    }

    @RequestMapping(path = "upload",method=RequestMethod.POST)
    @ResponseBody
    public String uploadAd(Advertise advertise){
        advertise.setLocationKey(getRandomString(10));
        advertise.setAdPos((short)5);
        advertise.setUpdateTime(new Date());
        boolean returnVal = advertiseService.uploadAd(advertise);
        return Message.search(returnVal);
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
