package com.efangtec.common.validate;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2019-01-28.
 */
public abstract class ValidateGroup {



    public static Map<String,Class> ValidateMap  = new ConcurrentHashMap<String,Class>();
    static {
       // ValidateMap.put(EfangtecConstant.MedicineName.QPL.getEnName()  , ValidateQPLGroup.class);
       // ValidateMap.put(EfangtecConstant.MedicineName.RBA.getEnName() ,   ValidateRBAGroup.class);
    }
    /**
     * 齐普乐
     */
    public interface ValidateQPLGroup {
       String apply="validator/qpl-apply.json";
    }

    /**
     * 瑞百安
     */
    public interface ValidateRBAGroup {
        String apply="validator/rba-apply.json";
    }
}
