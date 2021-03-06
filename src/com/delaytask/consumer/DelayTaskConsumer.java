package com.delaytask.consumer;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.delaytask.DelayTaskConsts;
import com.delaytask.callback.ICallBack;
import com.delaytask.callback.relolver.AbstractCallBackRelolver;
import com.delaytask.callback.relolver.ICallBackRelolver;
import com.delaytask.exception.IDelayTaskExceptionHandler;
import com.delaytask.queue.DelayTaskQueue;
import com.delaytask.queue.DelayTaskQueueElement;

/**
 * 延迟任务队列消费者类，负责启动消费服务以及队列扫描（注意：每个服务节点一个单例，注入时一定要单例singleton）
 *
 * @author sunjie
 */
public class DelayTaskConsumer implements Runnable {

    private static volatile DelayTaskConsumer instance;

    /**
     * 频率
     */
    private Integer circleFrequencyMilliSecond = DelayTaskConsts.CIRCLE_FREQUENCY_MILLI_SECOND;
    /**
     * 无任务时频率倍数
     */
    private Integer notTasksPower = DelayTaskConsts.NOT_TASK_SPOWER;
    /**
     * 消费队列
     */
    private DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue;
    /**
     * 运行标记
     */
    private Boolean isRun = false;
    /**
     * 消费任务时的线程池
     */
    private ExecutorService consumeTaskThreadPool;
    /**
     * 回调任务解释器
     */
    private ICallBackRelolver callBackRelolver;

    /**
     * 任务执行异常处理器
     */
    private IDelayTaskExceptionHandler exceptionHandler;

    /**
     * 私有构造类，目的为单例
     */
    private DelayTaskConsumer() {
    }

    /**
     * 初始化
     *
     * @param delayTaskQueue   消费队列
     * @param callBackRelolver 回调解释器
     * @return DelayTaskConsumer
     * @throws
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    public static DelayTaskConsumer getInstance(DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue,
                                                ICallBackRelolver callBackRelolver, IDelayTaskExceptionHandler exceptionHandler) {
        if (instance == null) {
            synchronized (DelayTaskConsumer.class) {
                if (instance == null) {
                    instance = new DelayTaskConsumer();
                    instance.delayTaskQueue = delayTaskQueue;
                    instance.consumeTaskThreadPool = Executors.newFixedThreadPool(8);
                    instance.callBackRelolver = callBackRelolver;
                    instance.exceptionHandler = exceptionHandler;
                }
            }
        }
        return instance;
    }

    /**
     * 初始化，默认使用反射解析器
     *
     * @param delayTaskQueue 消费队列
     * @return DelayTaskConsumer
     * @throws
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    public static DelayTaskConsumer getInstance(DelayTaskQueue<DelayTaskQueueElement> delayTaskQueue) {
        if (instance == null) {
            synchronized (DelayTaskConsumer.class) {
                if (instance == null) {
                    instance = new DelayTaskConsumer();
                    instance.delayTaskQueue = delayTaskQueue;
                    instance.consumeTaskThreadPool = Executors.newFixedThreadPool(8);
                    instance.callBackRelolver = new AbstractCallBackRelolver() {
                        @Override
                        public ICallBack getCallBackInstance() {
                            // 获取Class
                            Class<ICallBack> callBackClass;
                            try {
                                callBackClass = (Class<ICallBack>) Class.forName(this.delayTaskElement.getName());
                                // 获取无参构造对象
                                return callBackClass.newInstance();
                            } catch (Exception e) {
                                return null;
                            }
                        }
                    };
                    instance.exceptionHandler = delayTaskElement -> {
                        System.out.println("132");
                        System.out.println("132");
                    };
                }
            }
        }
        return instance;
    }

    public void startRun() throws Exception {
        if (isRun) {
            throw new Exception("======DelayTaskConsumer startRun is has running======");
        }
        // 改运行标记
        isRun = true;
        // 单起线程执行消费，不能直接在主线程中执行，会阻断服务启动
        // AsynService.getProxy不会创建线程执行，所以直接使用单线程池
        Executors.newSingleThreadExecutor().execute(this);
    }

    public void stopRun() throws Exception {
        // 改运行标记
        isRun = false;
    }

    public Boolean getIsRun() {
        return isRun;
    }

    public void getCircleFrequency(Integer circleFrequencyMilliSecond, Integer notTasksPower) {
        this.circleFrequencyMilliSecond = circleFrequencyMilliSecond;
        this.notTasksPower = notTasksPower;
    }

    @Override
    public void run() {
        Calendar calendar;
        int nowSecond;
        DelayTaskQueueElement delayTaskElement;
        while (true) {
            // 延迟一定事件后继续扫描。延时是防止过于频繁的访问队列
            try {
                Thread.sleep(circleFrequencyMilliSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 停止消费则跳出自旋
            if (!isRun) {
                break;
            }
            // 注意：多个服务节点会多次调用
            delayTaskElement = delayTaskQueue.peek();
            // 如果队列中没有任务，则继续扫描
            if (delayTaskElement == null) {
                // 延时是防止过于频繁的访问redis，导致命中率降低
                try {
                    Thread.sleep(circleFrequencyMilliSecond * notTasksPower);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            calendar = Calendar.getInstance();
            nowSecond = (int) (calendar.getTimeInMillis() / 1000);
            double delayTime = delayTaskElement.getDelayTime();
            if (nowSecond < delayTime) {
                continue;
            }
            try {
                // 开始消费
                // 1.移除队列中任务。TODO 这里有风险：如果回调执行失败，任务移除找不回
                // 2.验证是否移除成功。为了防止多个服务节点重复执行callback，只有成功remove的那个节点会执行callback，其他节点不执行
                if (delayTaskQueue.remove(delayTaskElement)) {
                    // 3.执行回调。为了避免callback执行耗时过长影响任务的消费，这里最好也为异步
                    callBackRelolver.setDelayTaskElement(delayTaskElement.getDelayTaskElement());
                    consumeTaskThreadPool.execute(callBackRelolver);
                }
            } catch (Exception e) {
                exceptionHandler.handle(delayTaskElement);
            }
        }
    }
}
