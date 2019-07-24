package com.oppo.weather.advertise.service.impl;

import com.oppo.weather.advertise.mapper.AdvertiseMapper;
import com.oppo.weather.advertise.model.Advertise;
import com.oppo.weather.advertise.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    private AdvertiseMapper advertiseMapper;

    @Override
    public boolean uploadAd(Advertise advertise) {
        return advertiseMapper.uploadAd(advertise);
    }
}
