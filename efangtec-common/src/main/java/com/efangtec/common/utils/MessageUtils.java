package com.efangtec.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**

 */

public class MessageUtils {

    //随机数生成器
    private static Random random = new Random();

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMssHHmmssSSS");

    /**
     * 生成消息id
     *
     * @param date
     * @return
     */
    public static String createMessageId(Date date) {
        String format = dateFormat.format(date);
        String mark = Long.toHexString(random.nextLong());
        return format.concat(mark);
    }
}
