package com.delaytask.queue;

/**
 * 
 * 延迟任务对调类型
 * 
 * @author sunjie
 *
 */
public enum EnumDelayTaskCallBackType {
    Spring("Spring"),

    Reflect("Reflect"),

    ICallBack("ICallBack");

    private String key;

    EnumDelayTaskCallBackType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
