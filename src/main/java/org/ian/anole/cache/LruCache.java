package org.ian.anole.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc lru cache util
 **/
public class LruCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxSize;

    public LruCache(int maxSize) {
        this(maxSize, 0.75F);
    }

    public LruCache(int maxSize, float loadFactor) {
        this(maxSize, loadFactor, true);
    }

    public LruCache(int maxSize, float loadFactor, boolean accessOrder) {
        super(maxSize, loadFactor, accessOrder);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }

}