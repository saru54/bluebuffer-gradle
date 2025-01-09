package com.core.util;

import org.springframework.stereotype.Component;

@Component
public class LocalStoreUtil {
    public ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    public String getUserId(){
        return threadLocal.get();
    }
    public void setUserId(String userId){
        threadLocal.set(userId);
    }
    public void clear(){
        threadLocal.remove();
    }
}
