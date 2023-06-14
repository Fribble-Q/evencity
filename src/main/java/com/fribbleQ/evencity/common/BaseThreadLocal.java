package com.fribbleQ.evencity.common;

public class BaseThreadLocal {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setBase(Long id){
        threadLocal.set(id);
    }

    public static Long getBase(){
        return threadLocal.get();
    }
}
