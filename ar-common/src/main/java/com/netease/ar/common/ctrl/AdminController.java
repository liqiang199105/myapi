package com.netease.ar.common.ctrl;

import com.google.common.collect.Maps;
import com.netease.ar.common.http.ApiResponseBody;
import com.netease.ar.common.http.ApiResponseBuilder;
import com.netease.ar.common.http.intercepter.AllowNoSignature;
import com.netease.ar.common.utils.RedisKeyUtil;
import com.netease.ar.common.utils.SpringAppConfig;
import org.apache.log4j.Logger;
import org.joda.time.Seconds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static Logger logger = Logger.getLogger(AdminController.class);

	@Resource(name = "jedisPool") private JedisPool jedisPool;


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


		// Redis Status
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.setex(RedisKeyUtil.PAINTER, 100, "1");
			map.put("app.version.jedis.status", jedis.exists(RedisKeyUtil.PAINTER));
		} catch (Exception e){
			map.put("app.version.jedis.status", false);
			logger.error(e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		logger.info(map);
		ApiResponseBuilder.build(response, new ApiResponseBody(map));
	}

}
