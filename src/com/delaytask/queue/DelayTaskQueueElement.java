package com.delaytask.queue;

import java.util.concurrent.TimeUnit;

public class DelayTaskQueueElement {

    private double delayTime;

    private TimeUnit delayTimeUint;

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
