package com.netease.ar.common.http;

import com.netease.ar.common.utils.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ApiResponseBuilder {
	private static Logger logger = Logger.getLogger(ApiResponseBuilder.class);

	public static void build(HttpServletResponse response, JSONObject result) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(result.toString());
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void build(HttpServletResponse response, Map<String, Object> result) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JsonUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void build(HttpServletResponse response, ApiResponseBody result) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JsonUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static <T> void buildCallback(HttpServletResponse response, T result, String callback){
		if (StringUtils.isNotBlank(callback)) {
			response.setContentType("text/javascript;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.append("var ").append(callback).append("=").append(JsonUtil.toJson(result)).append(";");
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			response.setContentType("application/json;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				JsonUtil.writeJson(result, writer);
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
