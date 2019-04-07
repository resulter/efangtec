package com.efangtec.passport.filter;

import com.alibaba.fastjson.JSONObject;
import com.efangtec.common.pojo.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2018-11-08.
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            JSONObject jsonObject  = new JSONObject();
            jsonObject.put("code", ResultCode.AUTHENTICATION.getCode());
            jsonObject.put("msg",ResultCode.AUTHENTICATION.getMessage() );
            jsonObject.put("data","");
            out = response.getWriter();
            out.append(jsonObject.toJSONString());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
