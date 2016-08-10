package com.netease.ar.common.utils;

import com.beust.jcommander.internal.Maps;
import com.netease.ar.common.BaseTest;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

public class QRUtilTest extends BaseTest {

    @Test
    public void encode(){
        int width = 300, height = 300;
        String fileName = "painter_";
        for (int i = 0; i < 10000; i++){
            String imgPath = "d:/work/qrcode/" + fileName + i + ".png";
            Map<String, Object> content = Maps.newHashMap();
            content.put("bookId", i);
            content.put("copyright", "painter.bobo.com");
            content.put("date", System.currentTimeMillis());
            ZXingCodeUtil.encode(JsonUtil.toJson(content), width, height, imgPath); // 默认生成二维码
            System.out.println("Hi ,you have finished zxing encode:" + fileName + i);
        }
    }
}
