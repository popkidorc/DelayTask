package com.delaytask;

/**
 * 
 * 延迟任务常量
 * 
 * @author sunjie
 *
 */
public class DelayTaskConsts {

    /** 延迟任务redis key */
    public static final String DELAY_TASK_ZSET_KEY = "DELAY_TASK_ZSET_KEY";

    /** 消费频率 */
    public static final Integer CIRCLE_FREQUENCY_MILLI_SECOND = 300;

    /** 无任务时频率倍数 */
    public static final Integer NOT_TASK_SPOWER = 1;
}
