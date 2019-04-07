package com.efangtec.common.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Administrator on 2019-02-19.
 */
public class MessageHolder {

    private static ThreadLocal<List<String>> messageHolder = new ThreadLocal<List<String>>() {

        @Override
        protected List<String> initialValue() {
            return Lists.newArrayList();
        }
    };

    public static List<String> get() {
        return messageHolder.get();
    }

    public static void set(String messageId) {
        List<String> list = messageHolder.get();
        list.add(messageId);
        messageHolder.set(list);
    }

    public static void clear() {
        messageHolder.remove();
    }
}
