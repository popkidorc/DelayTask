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

    void setDelayTaskElement(DelayTaskElement delayTaskElement);

    ICallBack getCallBackInstance();
}
