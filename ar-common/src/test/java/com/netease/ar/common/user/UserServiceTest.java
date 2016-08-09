package com.netease.ar.common.user;


import com.netease.ar.common.BaseTest;
import com.netease.ar.common.http.intercepter.ApiSignatureUtil;
import com.netease.ar.common.service.user.UserService;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

public class UserServiceTest extends BaseTest {

    @Resource private UserService userService;

    @Test
    public void testGetUserInfo(){
        System.out.println(userService.get("1"));
    }

    @Test
    public void testRegister() throws UnsupportedEncodingException {
        String phone = "13552862889";
        String verifyCode = "123456";
        userService.register(phone, verifyCode);
//        StringBuilder sb = new StringBuilder();
//        sb.append("123abc");
//        System.out.println( DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")).toLowerCase());
    }

}
