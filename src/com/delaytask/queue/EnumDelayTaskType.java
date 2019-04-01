package com.delaytask.queue;

public enum EnumDelayTaskType {
    Spring("Spring"),

    Reflect("Reflect"),

    ICallBack("ICallBack");

    private String key;

    EnumDelayTaskType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
