package com.netease.ar.common.utils;

public class RedisKeyUtil {
    public static final String PAINTER = "Painter#";


    /**
     * 手机验证码缓存
     * @param phone
     * @return
     */
    public static String getUserVerifyCodeKey(final String phone){
        return PAINTER + phone + "#";
    }

    /**
     * 获取用户的token
     * @param userId
     * @return
     */
    public static String getUserToken(final String userId){
        return PAINTER + userId + "#";
    }
}
