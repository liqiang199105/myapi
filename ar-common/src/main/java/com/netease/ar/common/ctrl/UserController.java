package com.netease.ar.common.ctrl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.netease.ar.common.http.exception.ApiError;
import com.netease.ar.common.http.exception.ApiException;
import com.netease.ar.common.http.ApiResponseBody;
import com.netease.ar.common.http.intercepter.AllowNoSignature;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.http.ApiResponseBuilder;
import com.netease.ar.common.utils.CommonUtil;
import com.netease.vshow.special.service.SMSForBoBoWebSercice;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);

	@Resource private UserService userService;
	@Resource @Qualifier("smsWebService") private SMSForBoBoWebSercice smsWebService;

	/**
	 * 获取手机验证码
	 * @param phone 手机号
	 * @param country 国家编码，例如中国86、台湾886
	 */
	@AllowNoSignature
	@RequestMapping(value = "getVerifyCode", method = RequestMethod.POST)
	public void getVerifyCode(@RequestParam(value = "phone", required = true) String phone,
							  @RequestParam(value = "country", defaultValue = "86") String country,
							  @RequestParam(value = "callback", required = false) String callback,
							  HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());
		if (!CommonUtil.isMobile(phone)){
			throw new ApiException(ApiError.USER_INVALID_PHONE);
		}
		String verifyCode = CommonUtil.getRandomString(6, true);
		String msg = "校验码：" + verifyCode + "，您正在验证手机号 " + phone + " 注意保密哦！";
		JSONObject jsonObject = JSONObject.fromObject( smsWebService.sendSMS(phone, msg, country));
		String respCode = jsonObject.getString("respCode");
		if ("10000".equals(respCode)) {
			throw new ApiException(ApiError.USER_INVALID_PHONE);
		} else if ("400".equals(respCode)) {
			throw new ApiException(ApiError.SMS_SEND_MESSAGE_FAILED);
		}
		userService.replaceVerifyCode(phone, verifyCode);
		Map<String, Object> result = Maps.newHashMap();
		result.put("msg", "发送验证码成功");
		result.put("verifyCode", verifyCode);
		result.put("phone", phone);
		ApiResponseBuilder.buildCallback(response, new ApiResponseBody(result), callback);
	}

	/**
	 * 用户注册登录
	 * @param phone
	 * @param verifyCode
     */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@AllowNoSignature
	public void reversion(@RequestParam(value = "phone", required = true) String phone,
						  @RequestParam(value = "verifyCode", required = true) String verifyCode,
						  @RequestParam(value = "callback", required = false) String callback,
						  HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());

		if (Strings.isNullOrEmpty(phone) || Strings.isNullOrEmpty(verifyCode)){
			throw new ApiException(ApiError.MISSING_REQUIRED_PARAMETER);
		}
		UserModel userModel = userService.register(phone, verifyCode);
		ApiResponseBuilder.buildCallback(response, new ApiResponseBody(userModel), callback);

	}

}
