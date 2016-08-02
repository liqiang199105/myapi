package com.netease.ar.common.user;


import com.netease.ar.common.BaseTest;
import com.netease.ar.common.service.user.UserService;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {

    @Resource private UserService userService;

    @Test
    public void testGetUserInfo(){
        System.out.println(userService.get(-9219412352562510775L));
    }


}
