package com.netease.ar.common.http.exception;

import org.springframework.http.HttpStatus;

public enum ApiError {

    // System
    INVALID_API_SYSTEM(1000001, "Invalid API"),
    INVALID_SESSIONKEY(1000002, "Invalid SessionKey (System)"),
    API_TIMESTAMP_OUT_OF_RANGE(1000003, "Request over time (System)"),
    INVALID_API_FORMAT(1000004, "Invalid response format"),
    INVALID_API_VERSION(1000005, "Invalid version, Please update your application."),
    INVALID_APP_CLIENT(1000006, "Invalid APP Client", HttpStatus.UNAUTHORIZED),
    INVALID_APP_KEY(1000007, "Invalid APP Key", HttpStatus.UNAUTHORIZED),
    INVALID_ENCRYPTION_METHOD(1000008, "Invalid encryption method"),
    INVALID_LANGUAGE(1000009, "Language is not supported"),
    INVALID_CURRENCY(1000010, "Currency is not supported"),
    SERVER_ERROR(1000015, "Server error", HttpStatus.INTERNAL_SERVER_ERROR),
    PERMISSION_DENIED(1000016, "Permission denied (System)", HttpStatus.FORBIDDEN),
    INVALID_SIGNATURE(1000018, "Invalid signature (System)"),
    MISSING_REQUIRED_PARAMETER(1000019, "Missing required parameters: {%s} (System)"),
    INVALID_PARAMETER(1000020, "Invalid parameter"),
    SERVER_BUSY(1000021,"Server Busy (System)"),

    // Phone
    USER_INVALID_PHONE(1002001, "Invalid phone number (Sms)"),
    USER_VERIFY_CODE_INCORRECT(1002002, "Incorrect verify code (Sms)"),
    USER_INVALID_PHONE_PWD(1002003, "Invalid phone or password (Sms)"),
    SMS_PHONE_BLACKLISTED(1002004, "Sms phone blacklisted (Sms)"),
    SMS_SEND_MESSAGE_FAILED(1002005, "Sending message failed. (Sms)"),

    ;


    private final int code;
    private final String message;
    private final HttpStatus status;
    private Object[] parameters = null;
    private ApiError(final int code, final String message, final HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
    private ApiError(final int code, final String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }

    public ApiError withParams(final Object... parameters) {
        this.parameters = parameters;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getCodeAsString() {
        return String.valueOf(code);
    }

    public String getMessage() {
        return (null == parameters) ? message : String.format(message, parameters);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ApiError fromCode(final int code) {
        for (ApiError error : values()) {
            if (error.code == code) {
                return error;
            }
        }
        throw new IllegalArgumentException("Illegal " + ApiError.class + " code: " + code);
    }

    @Override
    public String toString() {
        return "[" + code + "]: " + getMessage();
    }
}
