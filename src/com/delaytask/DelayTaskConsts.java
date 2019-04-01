package com.delaytask;

public class DelayTaskConsts {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String PORT = "6379";
    private static final String USER_LAST_AUDIT_OPERATE_TIME_ZSET_KEY = "USER_LAST_AUDIT_OPERATE_TIME_ZSET";

    /** 延迟任务redis key */
    public static final String DELAY_TASK_ZSET_KEY = "DELAY_TASK_ZSET_KEY";

    /** 消费频率 */
    public static final Integer CIRCLE_FREQUENCY_MILLI_SECOND = 300;

    /** 无任务时频率倍数 */
    public static final Integer NOT_TASK_SPOWER = 1;
}
