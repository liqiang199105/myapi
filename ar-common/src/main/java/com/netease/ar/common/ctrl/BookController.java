package com.netease.ar.common.ctrl;

import com.google.common.base.Strings;
import com.netease.ar.common.http.ApiResponseBody;
import com.netease.ar.common.http.ApiResponseBuilder;
import com.netease.ar.common.http.exception.ApiError;
import com.netease.ar.common.http.exception.ApiException;
import com.netease.ar.common.http.intercepter.AllowNoSignature;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.utils.CommonUtil;
import com.netease.vshow.special.service.SMSForBoBoWebSercice;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "book")
public class BookController {
	private static Logger logger = Logger.getLogger(BookController.class);

	@Resource private UserService userService;

	/**
	 * 获取图书信息。
	 * @param phone 手机号
	 */
	@AllowNoSignature
	@RequestMapping(value = "getBookInfo", method = RequestMethod.GET)
	public void getBookInfo(@RequestParam(value = "phone", required = true) String phone,
							  @RequestParam(value = "callback", required = false) String callback,
							  HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());

		ApiResponseBuilder.buildCallback(response, new ApiResponseBody("getBookInfo"), callback);
	}


}
