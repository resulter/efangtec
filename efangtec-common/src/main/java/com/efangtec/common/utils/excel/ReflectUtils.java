package com.efangtec.common.utils.excel;

import java.lang.reflect.Field;

public class ReflectUtils {
    public static Field[] getClassFieldsAndSuperClassFields(Class c){
        return c.getDeclaredFields();
    }
}
