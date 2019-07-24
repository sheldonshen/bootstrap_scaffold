package com.oppo.weather.advertise.enums;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
public enum Message {

    SUCCESS(true,"success"),ERROR(false,"error");

    private boolean boolValue;

    private String msg;

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
        //todo warn日志
        return "unknown";
    }
}
