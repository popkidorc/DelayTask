package com.delaytask.exception;

import com.delaytask.queue.DelayTaskQueueElement;

/**
 *
 * 延迟任务异常处理类
 *
 * @Author kid
 * @Date2020-03-19 19:49
 **/
public interface IDelayTaskExceptionHandler {

    /**
     *
     * 异常处理
     *
     * @param delayTaskElement
     */
    void handle(DelayTaskQueueElement delayTaskElement);
}
