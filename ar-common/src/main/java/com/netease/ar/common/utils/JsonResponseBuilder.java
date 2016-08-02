package com.netease.ar.common.utils;

import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonResponseBuilder {
	private static Logger logger = Logger.getLogger(JsonResponseBuilder.class);



	@Deprecated
	public static String build(HttpServletResponse response, JSONObject result) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(result.toString());
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String buildResp(HttpServletResponse response, Map<String, Object> result) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JsonUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String buildSuccResp(HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("success", 200);
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JsonUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String buildErrorResp(HttpServletResponse response, Integer errorCode, String errorMessage) {
		return buildErrorResp(response,errorCode,errorMessage,null);
	}
	
	public static String buildErrorResp(HttpServletResponse response, Integer errorCode, String errorMessage, Map<String,Object> extInfo) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("success", errorCode);
		result.put("error", errorMessage);
		if(extInfo!=null){
			result.putAll(extInfo);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JsonUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String buildResp(HttpServletResponse response, Map<String, Object> result, String callback) {
		if (StringUtils.isNotBlank(callback)) {
			response.setContentType("text/javascript;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.append(callback).append('(')
						.append(JsonUtil.toJson(result)).append(");");
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
		return null;
	}

	public static <T> void buildRespCallback(HttpServletResponse response, T result, String callback){
		if (StringUtils.isNotBlank(callback)) {
			response.setContentType("text/javascript;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.append("var ").append(callback).append("=").append(JsonUtil.toJson(result)).append(";");
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}else{
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
