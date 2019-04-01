package com.delaytask;

import com.delaytask.callback.ICallBack;

/**
 * 
 * 延迟任务主API
 * 
 * @author sunjie
 *
 */
public interface IDelayTask {

    /**
     * 
     * 延迟任务
     * 
     * @param callBackClass
     *            回调类
     * @param callBackParams
     *            回调参数
     * @param delaySecond
     *            延迟时间
     * @return void
     * @exception
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    public void handle(Class<? extends ICallBack> callBackClass, String[] callBackParams, Integer delaySecond);
}
