package com.oppo.weather.advertise.mapper;

import com.oppo.weather.advertise.model.Advertise;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertiseMapperTest {

    @Autowired
    private AdvertiseMapper advertiseMapper;

    @Test
    public void uploadAd() {
        Advertise advertise = new Advertise();
        advertise.setLocationKey("uuid_11123");
        advertise.setAdPos((short) 3);
        advertise.setAdLink("ad link");
        advertise.setUpdateTime(new Date());
        Assert.assertTrue(advertiseMapper.insertAd(advertise));
    }

}

