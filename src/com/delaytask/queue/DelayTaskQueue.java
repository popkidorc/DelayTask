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

    /**
     * 
     * 添加任务至队列
     * 
     * @param e
     * @return
     * @return boolean
     * @exception
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    boolean add(E e);

    /**
     * 
     * 移除队列中指定任务
     * 
     * @param e
     * @return
     * @return boolean
     * @exception
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    boolean remove(E e);

    /**
     * 
     * 拿出队列头部元素，但并不移除它
     * 
     * @return
     * @return E
     * @exception
     * @createTime：2019年4月1日
     * @author: sunjie
     */
    E peek();

}
