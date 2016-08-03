package com.netease.ar.common.service.user.impl;

import com.netease.ar.common.dao.user.UserDao;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource private UserDao userDao;

    @Override
    public UserModel get(final String userId){
        return userDao.get(userId);
    }

    @Override
    public UserModel register(final String phone, final String verifyCode){
        userDao.replacePhoneVerifyCode(new UserModel());
        return userDao.get(phone);
    }
}
