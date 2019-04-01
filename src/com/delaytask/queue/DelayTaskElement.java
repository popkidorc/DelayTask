package com.delaytask.queue;

import java.util.Arrays;

/**
 * 
 * 延迟任务回调任务元素
 * 
 * @author sunjie
 *
 */
public class DelayTaskElement {

    /**
     * 回调name
     */
    private String name;
    /**
     * 回调参数
     */
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

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DelayTaskElement [name=" + name + ", params=" + Arrays.toString(params) + "]";
    }

}
