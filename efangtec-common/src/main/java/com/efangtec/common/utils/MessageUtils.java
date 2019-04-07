package com.efangtec.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**

 */

public class MessageUtils {

    //�����������
    private static Random random = new Random();

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMssHHmmssSSS");

    /**
     * ������Ϣid
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
