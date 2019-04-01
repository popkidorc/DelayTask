package com.delaytask;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.delaytask.callback.ICallBack;
import com.delaytask.queue.DelayTaskElement;
import com.delaytask.queue.DelayTaskQueue;
import com.delaytask.queue.DelayTaskQueueElement;
import com.delaytask.queue.EnumDelayTaskType;

public class DelayTask implements IDelayTask {

    // 使用redis作为队列
    private DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue;

    public DelayTask(DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue) {
        super();
        this.delayTaskQueue = delayTaskQueue;
    }

    @Override
    public void handle4Reflect(Class<? extends ICallBack> class1, String[] callBackParams, Integer delaySecond) {
        this.handle(class1.getName(), EnumDelayTaskType.Reflect, callBackParams, delaySecond);
    }

    @Override
    public void handle4SpringBean(String callBackBeanName, String[] callBackParams, Integer delaySecond) {
        this.handle(callBackBeanName, EnumDelayTaskType.Spring, callBackParams, delaySecond);
    }

    @Override
    public void handle4Autowired(ICallBack callBack, String[] callBackParams, Integer delaySecond) {
        // 第一个参数暂时没用,仅作为强类型验证
        this.handle("@Autowired " + ICallBack.class.getSimpleName(), EnumDelayTaskType.ICallBack, callBackParams,
                delaySecond);
    }

    /**
     * 
     * 生产任务放入队列
     * 
     * @param callBackName
     * @param callBackParams
     * @param delaySecond
     * @return void
     * @exception
     * @createTime：2019年3月28日
     * @author: sunjie
     */
    private void handle(String callBackName, EnumDelayTaskType delayTaskType, String[] callBackParams,
            Integer delaySecond) {
        Calendar calendar = Calendar.getInstance();
        if (delaySecond == null || callBackName == null) {
            System.out.println("DelayTask handle ERROR, delaySecond:" + delaySecond + ", callBackName:" + callBackName);
            return;
        }
        calendar.add(Calendar.SECOND, delaySecond);
        int second3later = (int) (calendar.getTimeInMillis() / 1000);
        DelayTaskQueueElement delayTaskQueueElement = new DelayTaskQueueElement();
        delayTaskQueueElement.setDelayTime(second3later);
        delayTaskQueueElement.setDelayTimeUint(TimeUnit.MILLISECONDS);
        DelayTaskElement delayTaskElement = new DelayTaskElement();
        delayTaskElement.setName(callBackName);
        delayTaskElement.setParams(callBackParams);
        delayTaskElement.setType(delayTaskType);
        delayTaskQueueElement.setDelayTaskElement(delayTaskElement);
        delayTaskQueue.add(delayTaskQueueElement);
    }
}
