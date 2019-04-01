package com.delaytask.queue;

import java.util.concurrent.TimeUnit;

/**
 * 
 * 延迟任务队列元素
 * 
 * @author sunjie
 *
 */
public class DelayTaskQueueElement {

    /**
     * 延时时间
     */
    private double delayTime;
    /**
     * 延时时间单位
     */
    private TimeUnit delayTimeUint;
    /**
     * 任务元素
     */
    private DelayTaskElement delayTaskElement;

    public double getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(double delayTime) {
        this.delayTime = delayTime;
    }

    public TimeUnit getDelayTimeUint() {
        return delayTimeUint;
    }

    public void setDelayTimeUint(TimeUnit delayTimeUint) {
        this.delayTimeUint = delayTimeUint;
    }

    public DelayTaskElement getDelayTaskElement() {
        return delayTaskElement;
    }

    public void setDelayTaskElement(DelayTaskElement delayTaskElement) {
        this.delayTaskElement = delayTaskElement;
    }

    @Override
    public String toString() {
        return "DelayTaskQueueElement [delayTime=" + delayTime + ", delayTimeUint=" + delayTimeUint
                + ", delayTaskElement=" + delayTaskElement + "]";
    }

}
