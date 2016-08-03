package com.netease.ar.common.utils;

import com.netease.ar.common.BaseTest;
import org.junit.Test;

public class QRUtilTest extends BaseTest {

    @Test
    public void encode(){
        QRCodeUtil.encode("www.bobo.com");
    }
}
