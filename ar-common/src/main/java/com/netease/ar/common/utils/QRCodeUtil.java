package com.netease.ar.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.log4j.Logger;

public class QRCodeUtil {
    private static Logger logger = Logger.getLogger(QRCodeUtil.class);

    public static BufferedImage encode(String content, int width, int height){
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = Maps.newHashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);//白边间隙
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            return bufferedImage;
        } catch (Exception e) {
            logger.error("QR Code Util Encode Error!");
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage encode(String content){
        return encode(content, 400, 400);
    }

    public static void main(String[] args) throws Exception {
        String text = "www.bobo.com/666666";
        System.out.println(QRCodeUtil.encode(text));
    }

}
