package org.ian.anole;


import org.ian.anole.time.DateUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Mike Ian
 * @date 2022/10/12
 **/
public class TestAnnotation {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException {
        String result = DateUtils.getTimeOffsetStr(DateUtils.now(), TimeUnit.DAYS, 1,
                DateUtils.YMD,
                DateUtils.YMD);
        System.out.println(result);
    }

}
