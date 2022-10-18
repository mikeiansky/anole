package org.ian.anole.cache;


import java.util.*;
import java.util.function.*;

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
     * create a cache container with cache size and expire time
     *
     * @param size      cache size
     * @param expire    expire time
     * @param predicate predicate that cache value is qualified
     * @param <K>       cache key
     * @param <V>       cache value
     * @return cache container
     */
    public static <K, V> CacheContainer<K, V> of(int size, long expire, Predicate<V> predicate) {
        return new CacheContainer<>(size, expire, predicate);
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

    private Predicate<V> predicate;

    /**
     * cache container
     *
     * @param size   cache size
     * @param expire expire time
     */
    private CacheContainer(int size, long expire) {
        this(size, expire, null);
    }

    private CacheContainer(int size, long expire, Predicate<V> predicate) {
        this.size = size;
        this.expire = expire;
        this.predicate = predicate;
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
     * real put cache value
     *
     * @param key cache key
     * @param v   cache vakue
     */
    private void putCacheValue(K key, V v) {
        if (v == null) {
            return;
        }
        if (predicate != null && !predicate.test(v)) {
            return;
        }
        cache.put(key, new CacheObject<>(v, expire));
    }

    /**
     * get cache value by cache key , and it will use fallback to create a new cache value if necessary
     *
     * @param key      cache key
     * @param fallback new cache object generate
     * @return cache object
     */
    public V getCacheValue(K key, Function<K, V> fallback) {
        CacheObject<V> cacheObject = cache.get(key);
        if (cacheObject != null) {
            V t = cacheObject.getValue();
            if (cacheObject.isExpire()) {
                if (fallback != null) {
                    V fresh = fallback.apply(key);
                    if (fresh != null) {
                        putCacheValue(key, fresh);
                    } else {
                        cache.remove(key);
                    }
                } else {
                    cache.remove(key);
                }
            }
            return t;
        } else {
            if (fallback != null) {
                V fresh = fallback.apply(key);
                if (fresh != null) {
                    putCacheValue(key, fresh);
                    return fresh;
                }
            }
        }
        return null;
    }

    /**
     * refresh cache value by async way if expired
     *
     * @param key           cache key
     * @param refreshAction async refresh
     * @param fallback      create cache is miss
     * @return cache value
     */
    public V getCacheValueAsync(K key, BiConsumer<K, BiConsumer<K, V>> refreshAction, Function<K, V> fallback) {
        CacheObject<V> cacheObject = cache.get(key);
        if (cacheObject != null) {
            V t = cacheObject.getValue();
            if (cacheObject.isExpire()) {
                if (fallback != null) {
                    // only expire
                    refreshAction.accept(key, (k, v) -> {
                        putCacheValue(key, v);
                    });
                } else {
                    cache.remove(key);
                }
            }
            return t;
        } else {
            if (fallback != null) {
                V fresh = fallback.apply(key);
                if (fresh != null) {
                    putCacheValue(key, fresh);
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
            putCacheValue(keyFunction.apply(t), t);
        }
    }

    /**
     * add cache value
     *
     * @param key        cache key
     * @param cacheValue cache value
     */
    public void addCacheValue(K key, V cacheValue) {
        if (cacheValue == null) {
            return;
        }
        putCacheValue(key, cacheValue);
    }

    /**
     * add cache map
     *
     * @param cacheValueMap cache value map that need to be cached
     */
    public void addCacheValue(Map<K, V> cacheValueMap) {
        if (cacheValueMap == null) {
            return;
        }
        for (K key : cacheValueMap.keySet()) {
            putCacheValue(key, cacheValueMap.get(key));
        }
    }

    /**
     * remove cache value
     *
     * @param key cache key
     */
    public void removeCacheValue(K key) {
        cache.remove(key);
    }

    public void asyncRefreshCacheValue(K key, Function<K, V> refreshFunction) {
        V v = refreshFunction.apply(key);
        addCacheValue(key, v);
    }

    /**
     * get cache value map by key list
     *
     * @param keyList     cache key list
     * @param keyFunction cache value generate function
     * @param fallback    cache value create when miss if necessary
     * @return cache value map
     */
    public Map<K, V> getCacheValueMap(List<K> keyList, Function<V, K> keyFunction, Function<List<K>, Map<K, V>> fallback) {
        return getCacheValueMap(new HashSet<>(keyList), keyFunction, ks -> {
            if(fallback == null){
                return null;
            }
            return fallback.apply(new ArrayList<>(ks));
        });
    }

    /**
     * get cache value map by key set
     *
     * @param keySet      cache key set
     * @param keyFunction cache value generate function
     * @param fallback    cache value create when miss if necessary
     * @return cache value map
     */
    public Map<K, V> getCacheValueMap(Set<K> keySet, Function<V, K> keyFunction, Function<Set<K>, Map<K, V>> fallback) {
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
        if (missKeyList.size() > 0 && fallback != null) {
            Map<K, V> fallbackValue = fallback.apply(missKeyList);
            if (fallbackValue != null) {
                addCacheValue(fallbackValue.values(), keyFunction);
                cacheValueMap.putAll(fallbackValue);
            }
        }
        return cacheValueMap;
    }

}
