package com.netease.ar.common.dao.user;


import com.netease.ar.common.model.user.UserModel;

public interface UserDao {

    public UserModel get(final String userId);

    public UserModel getUserByPhone(final String phone);

    public void replaceUserVerifyCode(final UserModel userModel);

    public UserModel insertUserModel(final UserModel userModel);
}
