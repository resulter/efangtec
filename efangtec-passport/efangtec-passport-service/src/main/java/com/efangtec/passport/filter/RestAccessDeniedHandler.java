package com.efangtec.passport.filter;

import com.alibaba.fastjson.JSONObject;
import com.efangtec.common.pojo.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 权限不足的返回值
 * Created by Administrator on 2018-11-08.
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(403);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            JSONObject jsonObject  = new JSONObject();
            jsonObject.put("code", ResultCode.ACCESS_DENIED.getCode());
            jsonObject.put("msg",ResultCode.ACCESS_DENIED.getMessage() );
            jsonObject.put("data","");
            out = response.getWriter();
            out.append(jsonObject.toJSONString());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}