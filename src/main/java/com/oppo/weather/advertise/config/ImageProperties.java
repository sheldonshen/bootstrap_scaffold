package com.oppo.weather.advertise.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ImageProperties {

    @Value("${spring.http.upload.img.dir}")
    private String imgDir;

    @Value("${spring.http.upload.img.suffix}")
    private String suffix;

    @Value("${spring.http.upload.img.height}")
    private int height;

    @Value("${spring.http.upload.img.width}")
    private int width;
}
