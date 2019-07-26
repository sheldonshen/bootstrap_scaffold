package com.oppo.weather.advertise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.oppo.weather.advertise.mapper")
public class WeatherAdvertiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAdvertiseApplication.class, args);
    }


    /*@Bean(name = "datasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceBuild(){
        return DataSourceBuilder.create().build();
    }*/

}
