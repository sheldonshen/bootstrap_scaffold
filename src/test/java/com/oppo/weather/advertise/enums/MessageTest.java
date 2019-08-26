package com.oppo.weather.advertise.enums;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    @Test
    public void testSearch() {
        Assert.assertEquals(Message.search(true), "success");
        Assert.assertEquals(Message.search(false), "error");
    }
}
