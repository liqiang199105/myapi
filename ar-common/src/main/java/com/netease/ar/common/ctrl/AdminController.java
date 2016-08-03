package com.netease.ar.common.ctrl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.netease.ar.common.exception.ApiError;
import com.netease.ar.common.exception.ApiException;
import com.netease.ar.common.http.ApiResponseBody;
import com.netease.ar.common.http.ApiResponseBuilder;
import com.netease.ar.common.utils.SpringAppConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static Logger logger = Logger.getLogger(AdminController.class);

	@RequestMapping(value = "/reversion", method = RequestMethod.GET)
	public void reversion(HttpServletRequest request, HttpServletResponse response) {
		SpringAppConfig configs[] = new SpringAppConfig[]{
				SpringAppConfig.APP_VERSION_MAVEN,
				SpringAppConfig.APP_VERSION_GIT_COMMIT,
				SpringAppConfig.APP_VERSION_GIT_BRANCH,
				SpringAppConfig.APP_VERSION_GIT_COMMIT_TIME
		};

		Map<String, Object> map = Maps.newLinkedHashMap();
		for (SpringAppConfig config : configs) {
			map.put(config.getKey(), config.getValue());
		}
		logger.info(map);
		ApiResponseBuilder.buildResp(response, new ApiResponseBody(map));
	}

}
