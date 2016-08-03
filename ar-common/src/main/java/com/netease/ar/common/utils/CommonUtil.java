package com.netease.ar.common.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * 验证手机号是否正确
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{4,8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 随机字符串
     * @param length
     * @param isDigital
     * @return
     */
    public static String getRandomString(int length, boolean isDigital) {
        String base = isDigital ? "1234567890" :"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
