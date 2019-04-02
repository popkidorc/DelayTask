package com.delaytask.callback.relolver;

import com.delaytask.callback.ICallBack;
import com.delaytask.queue.DelayTaskElement;

/**
 * 
 * 回调任务，负责消费队列中元素（即调用callback）
 * 
 * @author sunjie
 *
 */
public abstract class AbstractCallBackRelolver implements ICallBackRelolver {

    /**
     * 队列中的element
     */
    protected DelayTaskElement delayTaskElement;

    public AbstractCallBackRelolver(DelayTaskElement delayTaskElement) {
        super();
        this.delayTaskElement = delayTaskElement;
    }

    public AbstractCallBackRelolver() {
        super();
    }

    public void setDelayTaskElement(DelayTaskElement delayTaskElement) {
        this.delayTaskElement = delayTaskElement;
    }

    /**
     * 
     * 获取CallBack实例
     * 
     * @return
     * @return ICallBack
     * @exception
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    public abstract ICallBack getCallBackInstance();

    @Override
    public void run() {
        getCallBackInstance().callBackMethod(delayTaskElement.getParams());
    }
}
