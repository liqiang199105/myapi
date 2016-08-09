package com.netease.ar.common.user;


import com.netease.ar.common.BaseTest;
import com.netease.ar.common.http.exception.ApiError;
import com.netease.ar.common.http.exception.ApiException;
import com.netease.ar.common.http.intercepter.ApiSignatureUtil;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.utils.CommonUtil;
import com.netease.vshow.special.service.SMSForBoBoWebSercice;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

public class UserServiceTest extends BaseTest {

    @Resource private UserService userService;
    @Resource @Qualifier("smsWebService") private SMSForBoBoWebSercice smsWebService;


    @Test
    public void testGetUserInfo(){
        System.out.println(userService.get("1"));
    }

    @Test
    public void testRegister() throws UnsupportedEncodingException {
        String phone = "13552862889";
        String verifyCode = "025311";
        userService.register(phone, verifyCode);
    }

    @Test
    public void testGetUserVerifyCode() throws UnsupportedEncodingException {
        String phone = "13552862889";
        String verifyCode = CommonUtil.getRandomString(6, true);
        String msg = "校验码：" + verifyCode + "，您正在验证手机号 " + phone + " 注意保密哦！";
        JSONObject jsonObject = JSONObject.fromObject( smsWebService.sendSMS(phone, msg, "86"));
        String respCode = jsonObject.getString("respCode");
        if ("10000".equals(respCode)) {
            throw new ApiException(ApiError.USER_INVALID_PHONE);
        } else if ("400".equals(respCode)) {
            throw new ApiException(ApiError.SMS_SEND_MESSAGE_FAILED);
        }
        userService.replaceVerifyCode(phone, verifyCode);
    }

}
