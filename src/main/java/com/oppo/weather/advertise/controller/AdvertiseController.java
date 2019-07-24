package com.oppo.weather.advertise.controller;


import com.oppo.weather.advertise.enums.Message;
import com.oppo.weather.advertise.model.Advertise;
import com.oppo.weather.advertise.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/advertise")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    @RequestMapping(path = "index",method = RequestMethod.GET)
    public String advertiseIndex(){
        //返回广告上传页面
        return "templates/template.ftl";
    }

    @RequestMapping(path = "upload",method=RequestMethod.POST)
    @ResponseBody
    public String uploadAd(Advertise advertise){
        boolean returnVal = advertiseService.uploadAd(advertise);
        return Message.search(returnVal);
    }
}
