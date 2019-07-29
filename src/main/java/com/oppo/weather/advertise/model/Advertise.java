package com.oppo.weather.advertise.model;

import lombok.Data;

import java.util.Date;

@Data
public class Advertise {

    private String locationKey;

    //广告位
    private short adPos;

    //广告链接
    private String adLink;

    private Date updateTime;

    /**-------------------------**/
    //一级标题
    private String firstClassTitle;

    //二级标题
    private String secondClassTitle;

    //上传广告图标(文件名称)
    private String adIconName;
}
