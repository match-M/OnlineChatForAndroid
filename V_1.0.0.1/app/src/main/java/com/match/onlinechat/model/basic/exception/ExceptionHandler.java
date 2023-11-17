package com.match.onlinechat.model.basic.exception;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author match
 * 这是用来处理全局异常的异常处理器，主要是为了防止app因为异常而崩溃
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionHandler instance;

    public synchronized static ExceptionHandler getInstance(){
        if(instance == null) instance = new ExceptionHandler();
        return instance;
    }

    public void init(Context ctx){
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        System.err.println(thread.getName()+":"+throwable);
    }
}
