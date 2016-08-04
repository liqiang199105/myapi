package com.netease.ar.common.http.intercepter;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.*;
import com.google.common.collect.ImmutableMap;
import com.netease.ar.common.http.exception.ApiError;
import com.netease.ar.common.http.exception.ApiException;
import com.netease.ar.common.utils.DateTimeUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ApiSignatureInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger(ApiSignatureInterceptor.class);

    private String enableUrlSignature;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            AllowNoSignature allowNoSignature = handlerMethod.getMethodAnnotation(AllowNoSignature.class);
            validateSystemParams(request, null != allowNoSignature);
        }
        return super.preHandle(request, response, handler);
    }

    private void validateSystemParams(final HttpServletRequest request, final boolean allowNoSignature) throws ApiException {
        Preconditions.checkNotNull(request);
        final String appKey = request.getParameter(ApiSignatureUtil.APP_KEY);
        if(Strings.isNullOrEmpty(appKey)) {
            throw new ApiException(ApiError.INVALID_APP_KEY);
        }
        if(null == ApiClient.getByKey(appKey)) {
            throw new ApiException(ApiError.INVALID_APP_CLIENT);
        }
        final String timestamp = request.getParameter(ApiSignatureUtil.TIMESTAMP);
        if(Strings.isNullOrEmpty(timestamp)) {
            throw new ApiException(ApiError.MISSING_REQUIRED_PARAMETER.withParams(ApiSignatureUtil.TIMESTAMP));
        }
        long current = System.currentTimeMillis();
        long timestampAsLong = Long.valueOf(timestamp);
        if (current < timestampAsLong || (current - timestampAsLong) > 1000 * 60){
            throw new ApiException(ApiError.API_TIMESTAMP_OUT_OF_RANGE);
        }

        if(Boolean.valueOf(enableUrlSignature) && allowNoSignature) {
            final String signature = request.getParameter(ApiSignatureUtil.SIGNATURE);
            if(Strings.isNullOrEmpty(signature)) {
                throw new ApiException(ApiError.MISSING_REQUIRED_PARAMETER.withParams(ApiSignatureUtil.SIGNATURE));
            }
            try {
                Map<String,String> parameters = getParametersForSign(request);
                final String calculatedSignature = ApiSignatureUtil.generateSignature(parameters);
                if(!calculatedSignature.equals(signature)) {
                    throw new ApiException(ApiError.INVALID_SIGNATURE);
                }
            } catch (Exception ex) {
                throw new ApiException(ApiError.INVALID_SIGNATURE, ex.getMessage());
            }
        }
    }


    public Map<String,String> getParametersForSign(HttpServletRequest request) throws Exception {
         Map<String,String> parametersAll =  Maps.newLinkedHashMap();
        for (String parameter : request.getParameterMap().keySet()) {
            parametersAll.put(parameter, Joiner.on(",").join(request.getParameterMap().get(parameter)));
        }
        return parametersAll;
    }

    public void setEnableUrlSignature(String enableUrlSignature) {
        this.enableUrlSignature = enableUrlSignature;
    }
}
