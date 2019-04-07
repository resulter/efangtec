package com.efangtec.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017-11-09.
 */
public class MybatisOgnl {

    /**
     * 可以用于判断 Map,Collection,String,Array Date是否为空
     * Number  = 0 认为是空不参与查询  insert
     *
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) throws IllegalArgumentException {

        if (o == null)
            return true;
        if (o instanceof String) {

            if(StringUtils.contains((String) o,"")){  //支持空字符串的insert
                return false;
            }

            if (StringUtils.isBlank((String) o)||"".equals(o)) {
                return true;
            }

            return false;// StringUtils.isEmpty((String) o);

        } else if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) o);

        } else if (o.getClass().isArray()) {
            return ArrayUtils.isEmpty((Object[]) o);

        } else if (o instanceof Map) {
            MapUtils.isEmpty((Map<?, ?>) o);

        } else if (o instanceof Date) {
            return o == null;

        } else if (o instanceof Number) {
            if (o instanceof Double) {
                if (((Number) o).doubleValue() == 0.0) {
                    return true;
                }
            }
            if (o instanceof Integer) {
                if (((Number) o).intValue() == 0) {
                    return true;
                }
            }

            if (o instanceof Long) {
                if (((Number) o).longValue() == 0) {
                    return true;
                }
            }
            return false;

        } else if (o instanceof Boolean) {
            return o == null;

        } else {
            throw new IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:" + o.getClass());
        }

        return false;
    }

    /**
     * 判断如果 object 为null 0 查询的时候不作为条件
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isZeroEmpty(Object o) throws IllegalArgumentException {
        if (o == null)
            return false;
        if (o instanceof String) {

            if (StringUtils.isBlank((String) o)) {
                return false;
            }
            return true;

        } else if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) o);

        } else if (o.getClass().isArray()) {
            return ArrayUtils.isEmpty((Object[]) o);

        } else if (o instanceof Map) {
            MapUtils.isEmpty((Map<?, ?>) o);

        } else if (o instanceof Date) {
            return o == null;

        } else if (o instanceof Number) {

            if (o instanceof Double) {
                if (((Number) o).doubleValue() == 0.0) {
                    return false;
                }
            }
            if (((Number) o).intValue() == 0) {
                return false;
            }

            if (((Number) o).longValue() == 0) {
                return false;
            }
            return true;

        } else if (o instanceof Boolean) {
            return o == null;

        } else {
            throw new IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:" + o.getClass());
        }

        return true;
    }

    public static boolean isNotZeroEmpty(Object o) {
        return !isZeroEmpty(o);
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotEmpty(Object... objects) {
        if (objects == null)
            return false;
        for (Object obj : objects) {
            if (isEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isBlank(Object o) {
        return StringUtils.isBlank((String) o);
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }
}
