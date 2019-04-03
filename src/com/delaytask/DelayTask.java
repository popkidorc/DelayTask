package com.delaytask;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.delaytask.callback.ICallBack;
import com.delaytask.queue.DelayTaskElement;
import com.delaytask.queue.DelayTaskQueue;
import com.delaytask.queue.DelayTaskQueueElement;

/**
 * 
 * 延迟任务主API
 * 
 * @author sunjie
 *
 */
public class DelayTask implements IDelayTask {

    /**
     * 消费队列
     */
    private DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue;

    public DelayTask(DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue) {
        super();
        this.delayTaskQueue = delayTaskQueue;
    }

    @Override
    public void handle(Class<? extends ICallBack> callBackClass, String[] callBackParams, Integer delaySecond) {
        Calendar calendar = Calendar.getInstance();
        if (delaySecond == null || callBackClass == null) {
            return;
        }
        calendar.add(Calendar.SECOND, delaySecond);
        int second3later = (int) (calendar.getTimeInMillis() / 1000);
        DelayTaskQueueElement delayTaskQueueElement = new DelayTaskQueueElement();
        delayTaskQueueElement.setDelayTime(second3later);
        delayTaskQueueElement.setDelayTimeUint(TimeUnit.MILLISECONDS);
        DelayTaskElement delayTaskElement = new DelayTaskElement();
        // 防止spring3.X的问题: XXX$$EnhancerBySpringCGLIB$$ed0005e9
        delayTaskElement.setName(callBackClass.getName().split("\\$")[0]);
        delayTaskElement.setParams(callBackParams);
        delayTaskQueueElement.setDelayTaskElement(delayTaskElement);
        // 生产任务放入队列
        delayTaskQueue.add(delayTaskQueueElement);
    }

}
