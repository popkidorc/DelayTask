package com.delaytask.queue;

/**
 * 
 * 延迟任务队列接口
 * 
 * @author sunjie
 *
 * @param <E>
 */
public interface DelayTaskQueue<E extends DelayTaskQueueElement> {

    boolean add(E e);

    boolean remove(E e);

    E peek();

}
