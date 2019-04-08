# DelayTask

```
DelayTaskQueue4RedisDemo delayTaskQueue4Redis = new DelayTaskQueue4RedisDemo();
// 运行消费端
DelayTaskConsumer.getInstance(delayTaskQueue4Redis).startRun();

// 延迟任务
DelayTask delayTask = new DelayTask(delayTaskQueue4Redis);
delayTask.handle(CallBackDemo.class, new String[] { "p1", "p2", "p3" }, 3);
```
