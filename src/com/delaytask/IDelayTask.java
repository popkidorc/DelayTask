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

    // /**
    // *
    // * 延迟任务。回调任务用class传入
    // *
    // * @param callBackClass
    // * @param callBackParams
    // * @param delaySecond
    // * @return void
    // * @exception
    // * @createTime：2019年3月28日
    // * @author: sunjie
    // */
    // public void handle4Reflect(Class<? extends ICallBack> callBackClass,
    // String[] callBackParams, Integer delaySecond);
    //
    // /**
    // *
    // * 延迟任务。回调任务用SpringBeanName传入,bean需要实现ICallBack
    // *
    // * @param callBackBeanName
    // * @param callBackParams
    // * @param delaySecond
    // * @return void
    // * @exception
    // * @createTime：2019年3月28日
    // * @author: sunjie
    // */
    // public void handle4SpringBean(String callBackBeanName, String[]
    // callBackParams, Integer delaySecond);
    //
    // /**
    // *
    // * 延迟任务。回调任务自动获取
    // *
    // * @param callBack
    // * @param callBackParams
    // * @param delaySecond
    // * @return void
    // * @exception
    // * @createTime：2019年3月28日
    // * @author: sunjie
    // */
    // public void handle4Autowired(ICallBack callBack, String[] callBackParams,
    // Integer delaySecond);

    /**
     * 
     * 延迟任务
     * 
     * @param callBackClass
     * @param callBackParams
     * @param delaySecond
     * @return void
     * @exception
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    public void handle(Class<? extends ICallBack> callBackClass, String[] callBackParams, Integer delaySecond);
}
