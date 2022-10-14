package org.ian.anole.safe;

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

}
