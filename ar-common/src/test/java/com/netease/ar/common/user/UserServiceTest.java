package com.netease.ar.common.user;


import com.netease.ar.common.BaseTest;
import com.netease.ar.common.service.user.UserService;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {

    @Resource private UserService userService;

    @Test
    public void testGetUserInfo(){
        System.out.println(userService.get("1"));
    }

    @Test
    public void testRegister(){
        String phone = "13552862889";
        String verifyCode = "123456";
        userService.register(phone, verifyCode);
    }


}
