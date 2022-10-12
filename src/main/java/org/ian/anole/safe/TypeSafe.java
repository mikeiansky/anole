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

}
