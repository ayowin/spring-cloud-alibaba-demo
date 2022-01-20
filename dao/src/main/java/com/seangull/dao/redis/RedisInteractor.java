package com.seangull.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @class: RedisInteractor
 * @brief: redis交互器，提供redis基本操作
 * @author: ouyangwei
 * @date: 2021.09.13
 */
@Component
public class RedisInteractor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /* Api For Key */

    /**
     * 指定缓存失效时间
     *
     * @param key
     * @param time, unit: second
     * @return
     */
    public boolean setExpire(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }

    /**
     * 获取key的过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void deleteKeys(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /* Api For String */

    /**
     * 普通缓存获取
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }

    /**
     * 普通放入缓存并设置时效
     *
     * @param key
     * @param value
     * @param time  time > 0 设置时效, time < 0 设置无限期，unit: second
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param delta: 增量
     * @return
     */
    public long increase(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子 不能小于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta: 增量
     * @return
     */
    public long decrease(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子,不能小于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /* Api For Hash */

    /**
     * 获取key表中的keyItem键值
     *
     * @param key
     * @param keyItem
     * @return
     */
    public Object getHash(String key, String keyItem) {
        return redisTemplate.opsForHash().get(key, keyItem);
    }

    /**
     * 获取key表中的所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 插入hash表
     *
     * @param key
     * @param map
     * @return
     */
    public boolean setHash(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
    }

    /**
     * 插入hash表并设置时效
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean setHash(String key, Map<Object, Object> map, Long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                setExpire(key, time);
            }
            return true;
        } catch (Exception e5) {
            e5.printStackTrace();
            return false;
        }
    }

    /**
     * 向hash表中插入键值
     *
     * @param key
     * @param itemKey
     * @param value
     * @return
     */
    public boolean setHash(String key, String itemKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, itemKey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向hash表中插入键值,设置时效
     *
     * @param key
     * @param itemKey
     * @param value
     * @param time
     * @return
     */
    public boolean setHash(String key, String itemKey, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, itemKey, value);
            if (time > 0) {
                setExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的项
     *
     * @param key  不能为null
     * @param itemKeys 不能为null,可以是多个
     */
    public void deleteHash(String key, Object... itemKeys) {
        redisTemplate.opsForHash().delete(key, itemKeys);
    }

    /**
     * 判断hash表中有没有键,项
     *
     * @param key
     * @param itemKey
     * @return
     */
    public boolean hasHashItem(String key, Object itemKey) {
        return redisTemplate.opsForHash().hasKey(key, itemKey);
    }

    /**
     * 递增,值如果不存在就会创建一个,并把创建的值返回
     *
     * @param key
     * @param itemKey
     * @param delta
     * @return
     */
    public double hashIncrease(String key, Object itemKey, double delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子不能小于0");
        }
        return redisTemplate.opsForHash().increment(key, itemKey, delta);
    }

    /**
     * 递减,值如果不存在就会创建一个,并把创建的值返回
     *
     * @param key
     * @param itemKey
     * @param delta
     * @return
     */
    public double hashDecrease(String key, Object itemKey, double delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子不能小于0");
        }
        return redisTemplate.opsForHash().increment(key, itemKey, -delta);
    }

    /* Api For Set */

    /**
     * 根据key获取set
     *
     * @param key
     * @return
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 从一个set中查询value是否存在
     *
     * @param key
     * @param value
     * @return
     */
    public boolean hasSetValue(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key
     * @param values
     * @return 成功的个数
     */
    public long setSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存并给key设置时效
     *
     * @param key    键
     * @param time   key 时效，unit: second
     * @param values 值,可以是多个
     * @return 成功的个数
     */
    public long setSet(String key, Long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                setExpire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存长度
     *
     * @param key
     * @return
     */
    public long getSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除set缓存中的值
     *
     * @param key    键
     * @param values 值可以是多个
     * @return 移除的个数
     */
    public long removeSetValue(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* Api For List */

    /**
     * 获取list缓存中的值
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  从0到-1显示所有的值
     * @return
     */
    public List<Object> getList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存长度
     *
     * @param key
     * @return
     */
    public long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据index获取list缓存的值
     *
     * @param key   键
     * @param index -1代表表尾,-2代表倒数第二个元素 以此类推
     * @return
     */
    public Object getListByIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把list放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setList(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 把list放入缓存,设置key的时效
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setList(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                setExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list缓存中具体值
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean updateListByIndex(String key, Long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long removeListByValue(String key, Long count, Object value) {
        try {
            long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
