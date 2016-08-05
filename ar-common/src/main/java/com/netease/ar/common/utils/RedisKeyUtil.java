package com.netease.ar.common.utils;

public class RedisKeyUtil {
    public static final String PAINTER = "Painter#";


    /**
     * 手机验证码缓存
     * @param phone
     * @return
     */
    public static String getUserVerifyCodeKey(String phone){
        return PAINTER + phone + "#";
    }
}
