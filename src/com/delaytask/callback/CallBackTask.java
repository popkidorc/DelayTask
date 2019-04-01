package com.delaytask.callback;

import com.delaytask.queue.DelayTaskElement;

/**
 * 
 * 回调任务，负责消费队列中元素（即调用callback）
 * 
 * @author sunjie
 *
 */
public class CallBackTask implements Runnable {

    // 队列中的element
    private DelayTaskElement delayTaskElement;

    public CallBackTask(DelayTaskElement delayTaskElement) {
        super();
        this.delayTaskElement = delayTaskElement;
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        System.out.println("======CallBackTask start======" + currentThread.getId());
        try {
            ICallBack callBack = null;
            switch (delayTaskElement.getType()) {
            // Spring的name方式
            case Spring: {
                // callBack = (ICallBack)
                // SpringContextUtil.getBean(element.getName());
                break;
            }
            // 反射方式
            case Reflect: {
                // 获取Class
                Class<ICallBack> callBackClass = (Class<ICallBack>) Class.forName(delayTaskElement.getName());
                // 获取无参构造对象
                callBack = callBackClass.newInstance();
                break;
            }
            // Spring的requiredType方式
            case ICallBack: {
                // callBack = (ICallBack)
                // SpringContextUtil.getBean(ICallBack.class);
                break;
            }
            default:
                break;
            }
            // 执行方法
            callBack.callBackMethod(delayTaskElement.getParams());
        } catch (Exception e) {
            System.out.println("======CallBackTask ERROR======" + currentThread.getId());
            e.printStackTrace();
        }
        System.out.println("======CallBackTask end======" + currentThread.getId());
    }
}
