# DelayTask
使用步骤：<
1、实现ICallBack接口，延迟任务到达延迟时间时会执行callBackMethod(String... params)方法，见“CallBack Demo”；    

2、实现DelayTaskQueue<DelayTaskQueueElement>接口，作为延迟任务的队列，见“Queue Demo”；    
   
   注：分布式服务请使用集中管理的队列，不要使用单机队列（例如redis、zk等）    
   
3、获取消费端实例（DelayTaskConsumer.getInstance），并执行startRun方法，开启消费服务；    

4、构造DelayTask类实例，设置延迟任务及回调。    


### CallBack Demo
```java
public class CallBackDemo implements ICallBack {

    public void callBackMethod(String... params) {
        for (String param : params) {
            System.out.println("param:" + param);
        }
    }
}
```

### Queue Demo
```java
public class DelayTaskQueue4RedisDemo implements DelayTaskQueue<DelayTaskQueueElement> {

    private static final String DELAY_TASK_ZSET_KEY = "DELAY_TASK_ZSET_KEY";
    // redis连接池
    private JedisPool jedisPool;
    // json转换工具
    private ObjectMapper objectMapper;

    public DelayTaskQueue4RedisDemo() {
        super();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        this.jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean add(DelayTaskQueueElement delayTaskQueueElement) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zadd(DELAY_TASK_ZSET_KEY, delayTaskQueueElement.getDelayTime(),
                    objectMapper.writeValueAsString(delayTaskQueueElement.getDelayTaskElement())) > 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean remove(DelayTaskQueueElement delayTaskQueueElement) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrem(DELAY_TASK_ZSET_KEY,
                    objectMapper.writeValueAsString(delayTaskQueueElement.getDelayTaskElement())) > 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
    }

    @Override
    public DelayTaskQueueElement peek() {
        Jedis jedis = jedisPool.getResource();
        try {
            Set<Tuple> tuples = jedis.zrangeWithScores(DELAY_TASK_ZSET_KEY, 0, 1);
            if (tuples == null || tuples.isEmpty()) {
                return null;
            }
            for (Tuple tuple : tuples) {
                DelayTaskQueueElement delayTaskQueueElement = new DelayTaskQueueElement();
                delayTaskQueueElement.setDelayTime(tuple.getScore());
                delayTaskQueueElement.setDelayTaskElement(objectMapper.readValue(tuple.getElement(),
                        DelayTaskElement.class));
                delayTaskQueueElement.setDelayTimeUint(TimeUnit.MILLISECONDS);
                return delayTaskQueueElement;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }
}
```




### Run Demo
```java
    DelayTaskQueue4RedisDemo delayTaskQueue4Redis = new DelayTaskQueue4RedisDemo();
    // 运行消费端
    DelayTaskConsumer.getInstance(delayTaskQueue4Redis).startRun();

    // 延迟任务
    DelayTask delayTask = new DelayTask(delayTaskQueue4Redis);
    delayTask.handle(CallBackDemo.class, new String[] { "p1", "p2", "p3" }, 3);
```
