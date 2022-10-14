package org.ian.anole.safe;

/**
 * @author Mike Ian
 * @date 2022/10/14
 * @desc char set utils
 **/
public class StringUtils {

    /**
     * predicate string is empty
     *
     * @param raw string that need to be predicated
     * @return true indicate is empty , false indicate is not empty
     */
    public static boolean isEmpty(String raw) {
        return raw == null || raw.length() == 0;
    }

    /**
     * predicate string has text
     *
     * @param raw string that need to be predicated
     * @return true indicate that it has text, false indicate that it has not text
     */
    public static boolean hasText(String raw) {
        return raw != null && raw.length() > 0;
    }

}
