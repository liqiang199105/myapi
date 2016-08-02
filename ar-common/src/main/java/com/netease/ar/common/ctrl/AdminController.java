package com.netease.ar.common.ctrl;

import com.google.common.collect.Maps;
import com.netease.ar.common.utils.JsonResponseBuilder;
import com.netease.ar.common.utils.SpringAppConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static Logger logger = Logger.getLogger(AdminController.class);

	@RequestMapping(value = "/reversion", method = RequestMethod.GET)
	public void reversion(HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURI());
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
		JsonResponseBuilder.buildResp(response, map);

	}

}
