package org.ian.anole.cache;

/**
 * @author Ian
 * @date 2022/9/15
 * @desc cache object
 **/
public class CacheObject<V> {

    private final V value;
    private final long expire;
    private long createTime;

    public CacheObject(V data, long expire) {
        this.value = data;
        this.expire = expire;
        this.createTime = System.currentTimeMillis();
    }

    /**
     * get real cache value
     *
     * @return cache value
     */
    public V getValue() {
        return value;
    }

    /**
     * refresh cache create time
     */
    public void refresh() {
        this.createTime = System.currentTimeMillis();
    }

    /**
     * predicate cache object is expire
     *
     * @return if true expired , if false not expired
     */
    public boolean isExpire() {
        if (expire == -1) {
            return false;
        }
        return System.currentTimeMillis() - createTime > expire;
    }

}
