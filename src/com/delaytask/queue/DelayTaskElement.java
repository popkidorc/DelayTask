package com.delaytask.queue;

import java.util.Arrays;

public class DelayTaskElement {

    private String name;

    private EnumDelayTaskType type;

    private String[] params;

    public DelayTaskElement() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumDelayTaskType getType() {
        return type;
    }

    public void setType(EnumDelayTaskType type) {
        this.type = type;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DelayTaskElement [name=" + name + ", type=" + type + ", params=" + Arrays.toString(params) + "]";
    }

}
