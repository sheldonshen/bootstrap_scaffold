package com.oppo.weather.advertise.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = -148117940294941578L;

    private String code;

    private String message;
}
