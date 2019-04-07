package com.efangtec.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018-02-05.
 */
public abstract class EfangtecJsonParser {

    public static  com.alibaba.fastjson.JSONArray getJSONArray(String ids){
        try {
            if(StringUtils.isNotBlank(ids)) {
                return   com.alibaba.fastjson.JSONArray.parseArray(ids);
            }else{
                return    com.alibaba.fastjson.JSON.parseArray("[]");
            }
        }catch (Exception e){
            System.out.println(ids);
            e.printStackTrace();
        }
        return   com.alibaba.fastjson.JSON.parseArray("[]");
    }

    public static  com.alibaba.fastjson.JSONObject getJSONObject(String ids){
        try {
            if(StringUtils.isNotBlank(ids)) {
                return  com.alibaba.fastjson.JSONObject.parseObject(ids);
            }else{
                return   com.alibaba.fastjson.JSONObject.parseObject("{}");
            }
        }catch (Exception e){
            System.out.println(ids);
            e.printStackTrace();
        }
        return   com.alibaba.fastjson.JSONObject.parseObject("{}");
    }
}
