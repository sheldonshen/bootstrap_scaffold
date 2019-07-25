package com.oppo.weather.advertise.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Message {

    SUCCESS(true,"success"),ERROR(false,"error");

    private boolean boolValue;

    private String msg;

    private static  final Logger logger = LoggerFactory.getLogger(Message.class);

    Message(boolean number,String msg){
        this.boolValue = number;
        this.msg = msg;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public String getMsg() {
        return msg;
    }

    public static String search(boolean number){
        for(Message message : Message.values()){
            if(message.getBoolValue() == number){
                return message.getMsg();
            }
        }
        logger.warn("can't find enum");
        return "unknown";
    }
}
