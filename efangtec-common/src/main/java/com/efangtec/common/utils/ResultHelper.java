package com.efangtec.common.utils;


import com.alibaba.fastjson.JSONObject;
import com.efangtec.common.pojo.Result;
import com.efangtec.common.pojo.ResultCode;


/**
 * Author: qing
 * Date: 14-10-14
 */
public class ResultHelper {

    /**
     * 在result组织数据的时候使用
     * @param result
     * @return
     */
    public static JSONObject renderAsJsonWipeData(Result result) {
        JSONObject source = new JSONObject();
        source.put("code",result.getResultCode().code);
        source.put("msg",result.getResultCode().getMessage());
        source.put("success",result.isSuccess());
        if(result.getMessage() != null) {
            source.put("msg",result.getMessage());
        }
        source.putAll(result.getAll());
        return source;
    }

    public static JSONObject renderAsJson(Result result) {
        JSONObject source = new JSONObject();
        source.put("code",result.getResultCode().code);
        source.put("msg",result.getResultCode().getMessage());
        source.put("success",result.isSuccess());
        if(result.getMessage() != null) {
            source.put("msg",result.getMessage());
        }
        source.put("data",result.getAll());
        return source;
    }

    public static JSONObject renderAsJson(Result result, String type) {
        JSONObject source = new JSONObject();
        source.put("code",0);
        source.put("msg",result.getResultCode().getMessage());
        source.put("success",result.isSuccess());
        if(result.getMessage() != null) {
            source.put("msg",result.getMessage());
        }
        source.put("count", result.getModel("count"));
        source.put("data",result.getModel("data"));
        if (result.getModel("other") != null) {
            source.put("other", result.getModel("other"));
        }
        return source;
    }

    public static JSONObject renderAsJson(ResultCode resultCode) {
        return renderAsJson(resultCode,null);
    }

    public static JSONObject renderAsJson(ResultCode resultCode,String message) {
        JSONObject source = new JSONObject();
        source.put("code",resultCode.code);
        source.put("msg",message);
        return source ;
    }


}
