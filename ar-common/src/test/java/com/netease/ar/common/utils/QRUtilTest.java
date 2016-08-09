package com.netease.ar.common.utils;

import com.netease.ar.common.BaseTest;
import org.junit.Test;

public class QRUtilTest extends BaseTest {

    @Test
    public void encode(){
        String fileName = "painter_";
        for (int i = 0; i < 10000; i++){
            String imgPath = "d:/work/qrcode/" + fileName + i + ".png";
            String contents = "http://www.bobo.com/" + fileName + i;
            int width = 300, height = 300;
            ZXingCodeUtil.encode(contents, width, height, imgPath); // 默认生成二维码
            System.out.println("Hi ,you have finished zxing encode:" + fileName + i);
        }
    }
}
