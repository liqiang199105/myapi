package com.netease.ar.common.service.user;


import com.netease.ar.common.model.user.UserModel;
import org.eclipse.jetty.server.Authentication;

public interface UserService {
    public UserModel get(final String userId);
    public UserModel register(final String phone, final String verifyCode);

}
