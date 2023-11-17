package com.match.onlinechat.model.basic.exception;

public class ConfigNameNotFoundException extends Exception {
    public ConfigNameNotFoundException(String errorInfo){
        super(errorInfo);
    }
}
