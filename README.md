# DelayTask

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

    private JedisPool jedisPool;

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
            String elementJson = objectMapper.writeValueAsString(delayTaskQueueElement.getDelayTaskElement());
            Long num = jedis.zadd(DELAY_TASK_ZSET_KEY, delayTaskQueueElement.getDelayTime(), elementJson);
            return num > 0;
        } catch (JsonProcessingException e) {
            System.out.println("DelayTaskQueue4Redis add is ERROR,, e:" + delayTaskQueueElement);
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
            String elementJson = objectMapper.writeValueAsString(delayTaskQueueElement.getDelayTaskElement());
            Long num = jedis.zrem(DELAY_TASK_ZSET_KEY, elementJson);
            return num > 0;
        } catch (JsonProcessingException e) {
            System.out.println("DelayTaskQueue4Redis remove is ERROR,, e:" + delayTaskQueueElement);
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
                DelayTaskElement delayTaskElement;
                delayTaskElement = objectMapper.readValue(tuple.getElement(), DelayTaskElement.class);
                delayTaskQueueElement.setDelayTaskElement(delayTaskElement);
                delayTaskQueueElement.setDelayTimeUint(TimeUnit.MILLISECONDS);
                return delayTaskQueueElement;
            }
            return null;
        } catch (Exception e) {
            System.out.println("DelayTaskQueue4Redis peek is ERROR, e:" + e);
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }
}
```




### Run Demo
```
DelayTaskQueue4RedisDemo delayTaskQueue4Redis = new DelayTaskQueue4RedisDemo();
// 运行消费端
DelayTaskConsumer.getInstance(delayTaskQueue4Redis).startRun();

// 延迟任务
DelayTask delayTask = new DelayTask(delayTaskQueue4Redis);
delayTask.handle(CallBackDemo.class, new String[] { "p1", "p2", "p3" }, 3);
```
