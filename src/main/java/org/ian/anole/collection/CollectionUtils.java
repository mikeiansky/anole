package org.ian.anole.collection;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc collection utils
 **/
public class CollectionUtils {

    /**
     * predicate collection is empty
     *
     * @param collection collection that need to predicate is empty
     * @return true indicate collection is empty, false indicate collection is not empty
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    /**
     * predicate collection is not empty
     *
     * @param collection collection that need to predicate is not empty
     * @return true indicate collection is not empty, false indicate collection is empty
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        if (collection == null) {
            return false;
        }
        return !collection.isEmpty();
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
        }
    }

}
