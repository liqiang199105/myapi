package com.netease.ar.common.ctrl;

import com.google.common.collect.Maps;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.utils.JsonResponseBuilder;
import com.netease.vshow.special.service.SMSForBoBoWebSercice;
import org.apache.log4j.Logger;
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
//	@Resource private SMSForBoBoWebSercice smsForBoBoWebSercice;


	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void reversion(@RequestBody UserModel userModel, HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());

		Map<String, Object> map = Maps.newLinkedHashMap();
		userService.get(0L);
		JsonResponseBuilder.buildResp(response, map);

	}

}
