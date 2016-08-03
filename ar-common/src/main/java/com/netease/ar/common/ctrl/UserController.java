package com.netease.ar.common.ctrl;

import com.google.common.collect.Maps;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.http.ApiResponseBuilder;
import com.netease.vshow.special.service.SMSForBoBoWebSercice;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);

	@Resource private UserService userService;
	@Resource @Qualifier("smsWebSercice") private SMSForBoBoWebSercice smsWebSercice;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void reversion(@RequestBody UserModel userModel, HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());

		Map<String, Object> map = Maps.newLinkedHashMap();
		userService.get("0");
		ApiResponseBuilder.buildResp(response, map);

	}


	@RequestMapping(value = "/sendAuthCode", method = RequestMethod.GET)
	public void sendAuthCode(HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());
		try {
			String phone = "13552862889";
			String message = "AR API";
			String ctcode = "86";
			JSONObject resp = JSONObject.fromObject(smsWebSercice.sendSMS(phone, message, ctcode));
			String code = resp.getString("respCode");
			if (code.equals("10000")) {
				logger.error("手机号不存在");
			} else if (code.equals("200")) {
				logger.info("短信发送成功");
			} else if (code.equals("400")) {
				logger.error("短信发送失败");
			}

		} catch (Exception e) {
			logger.error("发送短信失败");
			e.printStackTrace();
		}
		ApiResponseBuilder.buildResp(response, Maps.<String, Object>newHashMap());

	}



}
