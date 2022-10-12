package org.ian.anole.cache;


import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc cache container
 **/
public class CacheContainer<K, V> {

    /**
     * the default expire time , unit is millisecond
     */
    private static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24 * 7;

    /**
     * the default cache size
     */
    private static final int DEFAULT_SIZE = 10000;

    /**
     * create a cache container with cache size and expire time
     *
     * @param size   cache size
     * @param expire expire time
     * @param <K>    cache key
     * @param <V>    cache value
     * @return cache container
     */
    public static <K, V> CacheContainer<K, V> of(int size, long expire) {
        return new CacheContainer<>(size, expire);
    }

    /**
     * create a cache container with default cache size and expire time
     *
     * @param <K> cache key
     * @param <V> cache value
     * @return cache container
     */
    public static <K, V> CacheContainer<K, V> of() {
        return new CacheContainer<>(DEFAULT_SIZE, DEFAULT_EXPIRE);
    }

    /**
     * real cache object size
     */
    private final int size;

    /**
     * real expire time , unit is millisecond
     */
    private final long expire;

    /**
     * lru cache holder
     */
    private final LruCache<K, CacheObject<V>> cache;

    /**
     * cache container
     *
     * @param size   cache size
     * @param expire expire time
     */
    private CacheContainer(int size, long expire) {
        this.size = size;
        this.expire = expire;
        cache = new LruCache<>(size);
    }

    /**
     * cache size
     *
     * @return cache size
     */
    public int getSize() {
        return size;
    }

    /**
     * expire time
     *
     * @return expire time
     */
    public long getExpire() {
        return expire;
    }

    /**
     * get cache value by cache key
     *
     * @param key cache key
     * @return cache value
     */
    public V getCacheValue(K key) {
        return getCacheValue(key, null);
    }

    /**
     * get cache value by cache key , and it will use feedback to create a new cache value if necessary
     *
     * @param key      cache key
     * @param feedback new cache object generate
     * @return cache object
     */
    public V getCacheValue(K key, Supplier<V> feedback) {
        CacheObject<V> cacheObject = cache.get(key);
        if (cacheObject != null) {
            V t = cacheObject.getValue();
            if (cacheObject.isExpire()) {
                if (feedback != null) {
                    V fresh = feedback.get();
                    if (fresh != null) {
                        cache.put(key, new CacheObject<>(fresh, expire));
                    } else {
                        cache.remove(key);
                    }
                } else {
                    cache.remove(key);
                }
            }
            return t;
        } else {
            if (feedback != null) {
                V fresh = feedback.get();
                if (fresh != null) {
                    cache.put(key, new CacheObject<>(fresh, expire));
                    return fresh;
                }
            }
        }
        return null;
    }

    /**
     * add cache object
     *
     * @param cacheValueList cache value list
     * @param keyFunction    cache value key generate
     */
    public void addCacheValue(Collection<V> cacheValueList, Function<V, K> keyFunction) {
        for (V t : cacheValueList) {
            cache.put(keyFunction.apply(t), new CacheObject<>(t, expire));
        }
    }

    /**
     * add cache value
     *
     * @param key        cache key
     * @param cacheValue cache value
     */
    public void addCacheValue(K key, V cacheValue) {
        cache.put(key, new CacheObject<>(cacheValue, expire));
    }

    /**
     * remove cache value
     *
     * @param key cache key
     */
    public void removeCacheValue(K key) {
        cache.remove(key);
    }

    /**
     * get cache value map by key list
     *
     * @param keyList     cache key list
     * @param keyFunction cache value generate function
     * @param feedback    cache value create when miss if necessary
     * @return cache value map
     */
    public Map<K, V> getCacheValueMap(List<K> keyList, Function<V, K> keyFunction, Function<Set<K>, Map<K, V>> feedback) {
        return getCacheValueMap(new HashSet<>(keyList), keyFunction, feedback);
    }

    /**
     * get cache value map by key set
     *
     * @param keySet      cache key set
     * @param keyFunction cache value generate function
     * @param feedback    cache value create when miss if necessary
     * @return caceh value map
     */
    public Map<K, V> getCacheValueMap(Set<K> keySet, Function<V, K> keyFunction, Function<Set<K>, Map<K, V>> feedback) {
        Map<K, V> cacheValueMap = new HashMap<>();
        Set<K> missKeyList = new HashSet<>();
        for (K k : keySet) {
            V t = getCacheValue(k, null);
            if (t != null) {
                cacheValueMap.put(k, t);
            } else {
                missKeyList.add(k);
            }
        }
        if (missKeyList.size() > 0 && feedback != null) {
            Map<K, V> feedbackValue = feedback.apply(missKeyList);
            if (feedbackValue != null) {
                addCacheValue(feedbackValue.values(), keyFunction);
                cacheValueMap.putAll(feedbackValue);
            }
        }
        return cacheValueMap;
    }

}
