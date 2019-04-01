package com.delaytask.queue;

public interface DelayTaskQueue<E extends DelayTaskQueueElement> {

    boolean add(E e);

    boolean remove(E e);

    E peek();

}
