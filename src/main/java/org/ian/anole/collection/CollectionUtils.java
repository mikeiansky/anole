package org.ian.anole.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc collection utils
 **/
public class CollectionUtils {

    /**
     * 获得去重后的集合列表
     *
     * @param list 集合数据
     * @return 去重后的集合数据
     */
    public static <T> List<T> toDistinctList(List<T> list) {
        return toDistinctList(list, Collections.emptyList());
    }

    /**
     * 获得去重后的集合列表
     *
     * @param list     集合数据
     * @param fallback 如果为空时的补偿处理
     * @return 去重后的集合数据
     */
    public static <T> List<T> toDistinctList(List<T> list, List<T> fallback) {
        if (list == null) {
            return fallback;
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取安全的集合类型
     *
     * @param list 原始集合数据
     * @return 非空的集合数据
     */
    public static <T> List<T> toSafeList(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * predicate collection is empty
     *
     * @param collection collection that need to predicate is empty
     * @return true indicate collection is empty, false indicate collection is not empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() > 0;
    }

    /**
     * predicate collection is not empty
     *
     * @param collection collection that need to predicate is not empty
     * @return true indicate collection is not empty, false indicate collection is empty
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * collection type convert
     *
     * @param source   from type collection
     * @param target   target type collection
     * @param supplier target instance object supplier
     * @param consumer convert type
     * @param <T>      source type
     * @param <R>      target type
     */
    public static <T, R> void convert(Collection<T> source, Collection<R> target, Supplier<R> supplier, BiConsumer<T, R> consumer) {
        if (isEmpty(source)) {
            return;
        }
        if (target == null) {
            return;
        }
        for (T s : source) {
            R t = supplier.get();
            consumer.accept(s, t);
            target.add(t);
        }
    }

}
