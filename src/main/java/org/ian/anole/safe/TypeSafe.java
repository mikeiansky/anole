package org.ian.anole.safe;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc type safe utils
 **/
public class TypeSafe {

    /**
     * avoid raw value is null occurs exception
     *
     * @param raw raw value
     * @return safe target value
     */
    public static int getValue(Integer raw) {
        if (raw == null) {
            return 0;
        }
        return raw;
    }

    /**
     * avoid raw value is null occurs exception
     *
     * @param raw raw value
     * @return safe target value
     */
    public static float getValue(Float raw) {
        if (raw == null) {
            return 0;
        }
        return raw;
    }

    /**
     * avoid raw value is null occurs exception
     *
     * @param raw raw value
     * @return safe target value
     */
    public static long getValue(Long raw) {
        if (raw == null) {
            return 0;
        }
        return raw;
    }

    /**
     * avoid raw value is null occurs exception
     *
     * @param raw raw value
     * @return safe target value
     */
    public static double getValue(Double raw) {
        if (raw == null) {
            return 0;
        }
        return raw;
    }

    public static boolean getValue(Boolean raw) {
        if (raw == null) {
            return false;
        }
        return raw;
    }

    public static boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return false;
        }
        if (o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    /**
     * parse string value to int with safe way
     *
     * @param raw string value that need to be parsed
     * @return int value
     */
    public static int parseInt(String raw) {
        if (StringUtils.isEmpty(raw)) {
            return 0;
        }
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * parse string value to float with safe way
     *
     * @param raw string value that need to be parsed
     * @return float value
     */
    public static float parseFloat(String raw) {
        if (StringUtils.isEmpty(raw)) {
            return 0;
        }
        try {
            return Float.parseFloat(raw);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * parse string value to long with safe way
     *
     * @param raw string value that need to be parsed
     * @return long value
     */
    public static long parseLong(String raw) {
        if (StringUtils.isEmpty(raw)) {
            return 0;
        }
        try {
            return Long.parseLong(raw);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * parse string value to double with safe way
     *
     * @param raw string value that need to be parsed
     * @return double value
     */
    public static double parseDouble(String raw) {
        if (StringUtils.isEmpty(raw)) {
            return 0;
        }
        try {
            return Double.parseDouble(raw);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取安全数据
     *
     * @param s     原始数据
     * @param apply 转换操作
     * @return 结果数据
     */
    public static <S, T> T getValue(S s, Function<S, T> apply) {
        if (s == null) {
            return null;
        }
        return apply.apply(s);
    }

    /**
     * 获取安全的数据
     *
     * @param map   集合数据
     * @param key
     * @param apply
     * @return
     */
    public static <K, S, T> T getValue(Map<K, S> map, K key, Function<S, T> apply) {
        if (map == null) {
            return null;
        }
        S s = map.get(key);
        if (s == null) {
            return null;
        }
        return apply.apply(s);
    }

}
