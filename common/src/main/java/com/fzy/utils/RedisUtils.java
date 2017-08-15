package com.fzy.utils;

import com.fzy.config.Global;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * redis操作
 * Created by fuzhongyu on 2017/3/19.
 */
public class RedisUtils {

    //Redis服务器IP
    private static String HOST = Global.getConfig("redis.host");
    //Redis的端口号
    private static int PORT = Integer.valueOf(Global.getConfig("redis.port"));

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 20;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 30;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     **/
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
        } catch (Exception e) {
                e.printStackTrace();
            }
    }

  //=================存储string================//

    /**
     * 获取字符串缓存
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {

        String value = null;
        Jedis jedis=getResource();
        if (jedis.exists(key)) {
            value = jedis.get(key);
        }

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }

    /**
     * 设置字符串缓存
     * @param key  键
     * @param value  值
     * @param cacheSeconds  缓存过期时间(秒)，0为不超时
     */
    public static String setString(String key,String value,int cacheSeconds){

        String result=null;
        Jedis jedis=getResource();
        result=jedis.set(key,value);
        //如果缓存过期时间不为0，则设置缓存过期时间
        if(cacheSeconds!=0){
            jedis.expire(key,cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }


//=============存储object=================//
    /**
     * redis本身并没有直接存储对象的方法，我们可以通过转换对象的方式来存储对象，主要有以下三种方法:
     *
     *     1,序列化对象为二进制    jedis.get(byte[] key);    jedis.set(byte[] key,byte[] value)
     *     2,序列化对象为字符串（常见的为转jsonString）  jedis.get(String key);   jedis.set(String key,String value)
     *     3,转换对象为Map   jedis.hgetAll(String key);   jedis.hmset(String key,Map<String,String> values)
     *
     */

    /**
     * 获取对象缓存
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key) {
        Object value = null;
        Jedis jedis = getResource();
        if (jedis.exists(getBytesKey(key))) {
            value = toObject(jedis.get(getBytesKey(key)));
        }

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }


    /**
     * 设置对象缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 缓存超时时间（秒），0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = getResource();
        result = jedis.set(getBytesKey(key), toBytes(value));
        if (cacheSeconds != 0) {
            jedis.expire(getBytesKey(key), cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }


//===================存储List=====================//
    /**
     * 在Jedis中其实并没有提供这样的API对对象，或者是List对象的直接缓存，即并没有如下类似的API:jedis.set(String key, List<M> values),
     * 但提供了jedis.set(byte[], byte[]),所以通过这个API，我们能够实现jedis.set(String key, List<M> values)
     *
     *
     * 注：这种方式是将list整体直接存储（即 存储object一样），对经常改变的list来说这种存储方式并不利于操作。
     */


    /**
     * 获取List缓存(不经常改变)
     * @param key 键
     * @return 值
     */
    public static List<Object> getUnchangeList(String key) {
        List<Object> value=null;
        Jedis jedis =  getResource();
        if (jedis.exists(getBytesKey(key))) {
           value=toObjectList(jedis.get(getBytesKey(key)));
        }

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }



    /**
     * 设置List缓存(不经常改变)
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间(秒)，0为不超时
     * @return
     */
    public static long setUnchangeList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis  = getResource();
        if (jedis.exists(getBytesKey(key))) {
            jedis.del(key);
        }
        jedis.set(getBytesKey(key),toBytesList(value));
        if (cacheSeconds != 0) {
            jedis.expire(getBytesKey(key), cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间(秒)，0为不超时
     * @return
     */
    @Deprecated
    public static long listAdd(String key, Object value,int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            List<Object> list=getUnchangeList(key);
            //如果存在该key则,加入到这个key的list中,否则创建
            if(list!=null&&value!=null){
                list.add(value);
            }

            result=setUnchangeList(key,list, (int) cacheSeconds);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }


    //----------------->

    /**
     * 下面提供api给出的list存储方法，这种就是便于对redis中list增删改查操作
     */

    /**
     * 获取List
     * @param key 键
     * @return
     */
    public static List<Object> getList(String key){
        return getList(key,0, -1);
    }

    /**
     * 获取List（指定其中某段）
     * @param key 键
     * @param start  开始位置
     * @param end  结束位置
     * @return
     */
    public static List<Object> getList(String key,int start,int end){
        List<Object> returnList=new ArrayList<>();
        Jedis jedis=getResource();
        if(jedis.exists(getBytesKey(key))){
            List<byte[]> list=jedis.lrange(getBytesKey(key),start,end);
            for (byte[] obj:list){
                returnList.add(toObject(obj));
            }
        }

        //归还资源
        returnBrokenResource(jedis);
        return returnList;
    }


    /**
     * 设置List缓存
     * @param key  键
     * @param value  值
     * @param cacheSeconds 超时时间(秒)，0为不超时
     * @return
     */
    public static long setList(String key,List<Object> value,int cacheSeconds){
        long result=0;
        Jedis jedis=getResource();
        result=listEndAdd(key,value.toArray());
        if(cacheSeconds!=0){
            result = jedis.expire(getBytesKey(key),0);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }




    /**
     * 向缓存List的尾部添加元素
     * @param key
     * @param value
     * @return
     */
    public static long listEndAdd(String key,Object... value){
        long result=0;
        Jedis jedis=getResource();
        for(Object obj:value){
            result=jedis.rpush(getBytesKey(key),toBytes(obj));
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }

    /**
     * 向缓存List的头部添加元素
     * @param key
     * @param value
     * @return
     */
    public static long listStartAdd(String key,Object... value){
        long result=0;
        Jedis jedis=getResource();
        for (Object obj:value){
            result=jedis.lpush(getBytesKey(key),toBytes(value));
        }


        return result;
    }

    /**
     * 获取List的长度
     * @param key
     * @return
     */
    public static long getListLength(String key){
        long length=0;
        Jedis jedis=getResource();
        if(jedis.exists(getBytesKey(key))){
            length= jedis.llen(getBytesKey(key));
        }
        return length;
    }


    /**
     * 获取指定索引的值
     * @param key  键
     * @param index  索引,0开始
     * @return
     */
    public static Object getObjectFromList(String key,int index) {
        Jedis jedis = getResource();
        if (jedis.exists(getBytesKey(key))) {
            return toObject(jedis.lindex(getBytesKey(key), index));
        }
        return null;
    }


//===================存储Map=====================//

    /**
     * 获取Map缓存 (返回哈希表key中所有域和值)
     * @param key 键
     * @return map
     */
    @Deprecated
    public static Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        Jedis jedis = getResource();
        if (jedis.exists(key)) {
            value = jedis.hgetAll(key);
        }
        //归还资源
        returnBrokenResource(jedis);
        return value;
    }

    //----------------->

    /**
     * 获取值
     * @param key 键
     * @param field 获取到的map中的键
     * @return 获取到的map中的值
     */
    @Deprecated
    public static String hget(String key, String field) {
        String value = null;
        Jedis jedis = getResource();
        if (jedis.hexists(key, field)) {
            value = jedis.hget(key, field);
        }

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }



    /**
     * 设置Map缓存
     * @param key  键
     * @param field Map的键
     * @param value Map的值
     * @param cacheSeconds  超时时间(秒)，0为不超时
     * @return
     */
    @Deprecated
    public static long hset(String key,String field,String value,int cacheSeconds){
        long result=0;
        Jedis jedis=jedisPool.getResource();
        result=jedis.hset(key,field,value);
        if(cacheSeconds!=0){
            jedis.expire(key,cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }


    /**
     * 获取多个值
     * @param key 键
     * @param fields Map的键(多个)
     * @return 对应的值集合
     */
    @Deprecated
    public static List hmget(String key, String... fields) {
        List value=null;
        Jedis jedis = getResource();
        value=jedis.hmget(key,fields);

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }

    /**
     * 设置Map缓存
     * @param key  键
     * @param map Map的键
     * @param cacheSeconds  超时时间(秒)，0为不超时
     * @return
     */
    @Deprecated
    public static String hmset(String key,Map<String,String> map,int cacheSeconds){
        String result=null;
        Jedis jedis=getResource();
        result=jedis.hmset(key, map);
        if(cacheSeconds!=0){
            jedis.expire(key,cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }

    //----------------->

    /**
     * 获取Object
     * @param key 键
     * @param field  Map的键
     * @return 值
     */
    public static Object hGetObject(String key, String field) {
        Object value = null;
        Jedis jedis = getResource();
        if (jedis.hexists(getBytesKey(key), getBytesKey(field))) {
            value = toObject(jedis.hget(getBytesKey(key), getBytesKey(field)));
        }

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }

    /**
     * 设置Map缓存
     * @param key  键
     * @param field Map的键
     * @param object Map的值
     * @param cacheSeconds  超时时间(秒)，0为不超时
     * @return
     */
    public static long hSetObject(String key,String field,Object object,int cacheSeconds){
        long result=0;
        Jedis jedis=getResource();
        result=jedis.hset(getBytesKey(key),getBytesKey(field),toBytes(object));
        if(cacheSeconds!=0){
            jedis.expire(getBytesKey(key),cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }


    /**
     * 设置Map缓存
     * @param key  键
     * @param map 要存储的Map
     * @param cacheSeconds  超时时间(秒)，0为不超时
     * @return
     */
    public static void hmSetMap(String key,Map<String,Object> map,int cacheSeconds){
        String result=null;
        Jedis jedis=getResource();
        for(String field:map.keySet()){
            hSetObject(key,field,map.get(field),cacheSeconds);
        }
        if(cacheSeconds!=0){
            jedis.expire(key,cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
    }

    /**
     * 删除Map中键为field的缓存
     * @param key 键
     * @param field Map的键
     * @return
     */
    public static long hDeleteObject(String key, String field) {
        long result = 0;
        Jedis jedis = getResource();
        result = jedis.hdel(getBytesKey(key), getBytesKey(field));

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }



//===================存储Set=====================//



    /**
     * 获取Set缓存
     * @param key 键
     * @return 值
     */
    public static Set<Object> getSet(String key) {
        Set<Object> value = new HashSet<>();
        Jedis jedis = getResource();
        if (jedis.exists(getBytesKey(key))) {
            Set<byte[]> set=jedis.smembers(getBytesKey(key));
            for(byte[] bytes:set){
                value.add(toObject(bytes));
            }
        }

        //归还资源
        returnBrokenResource(jedis);
        return value;
    }


    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = getResource();
        for (Object obj:value){
            result = setAdd(key,obj);
        }
        if (cacheSeconds != 0) {
            jedis.expire(key, cacheSeconds);
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }

    /**
     * 往Set中添加元素
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setAdd(String key,Object... value){
        long result=0;
        Jedis jedis=getResource();
        for(Object obj:value){
            result = jedis.sadd(getBytesKey(key),toBytes(obj));
        }

        //归还资源
        returnBrokenResource(jedis);
        return result;
    }



//===================其他的jedis常用方法=====================//

    /**
     * 获取资源
     * @return jedis
     */
    public static Jedis getResource(){
        if(jedisPool!=null){
          return jedisPool.getResource();
        }
        return null;
    }

    /**
     * 归还资源
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 清空所有的key
     * @param jedis
     */
    public static void flushRedisCache(Jedis jedis){
        if(jedis!=null){
            jedis.flushAll();
        }
    }

    /**
     * 列出所有Key
     * @param jedis
     * @return
     */
    public static Set allKeys(Jedis jedis){
        if (jedis!=null){
            return jedis.keys("*");
        }
        return null;
    }


    /**
     * 设置某个键的过期时间
     * @param jedis
     * @param key
     * @param unixTime 时间戳
     * @return
     */
    public static long expireAt(Jedis jedis,String key,long unixTime){
        if(jedis!=null&&jedis.exists(key)){
            return jedis.expireAt(key,unixTime);

        }
        return 0;
    }



//===================上面用到的类型转换方法=====================//

    /**
     * 获取byte[]类型Key
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object){
        if(object instanceof String){
            try {
                return ((String)object).getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return SerializableUtils.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object){
        return SerializableUtils.serialize(object);
    }

    /**
     * byte[]型转换Object
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes){
        return SerializableUtils.unserialize(bytes);
    }

    /**
     * List<Object>转换byte[]类型
     * @param list
     * @return
     */
    public static byte[] toBytesList(List<Object> list){
        return SerializableUtils.serializeList(list);
    }

    /**
     * byte[]型转换List<Object>
     * @param bytes
     * @return
     */
    public static List<Object> toObjectList(byte[] bytes){
        return SerializableUtils.unSerializeList(bytes);
    }

}
