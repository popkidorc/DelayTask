package com.delaytask.callback.relolver;

import com.delaytask.callback.ICallBack;
import com.delaytask.queue.DelayTaskElement;

/**
 * 
 * 回调任务解析器
 * 
 * @author sunjie
 *
 */
public interface ICallBackRelolver extends Runnable {

    /**
     * 
     * 设置延迟任务元素
     * 
     * @param delayTaskElement
     * @return void
     * @exception
     * @createTime：2019年4月2日
     * @author: sunjie
     */
    void setDelayTaskElement(DelayTaskElement delayTaskElement);

    /**
     * 
     * 获取延迟任务实例
     * 
     * @return
     * @return ICallBack
     * @exception
     * @createTime：2019年4月2日
     * @author: sunjie
     */
    ICallBack getCallBackInstance();
}
