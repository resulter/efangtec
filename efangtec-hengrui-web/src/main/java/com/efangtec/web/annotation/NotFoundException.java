package com.efangtec.web.annotation;

import com.efangtec.common.pojo.Constants;
import com.efangtec.common.utils.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 404异常处理
 * 获取request请求头，如果包含type字段，则提示需要添加药品类型
 *                  如果不包含，正常404即可
 * 已知@ControllerAdvice + @ExceptionHandle可以处理 除 404 以外的 运行异常，那么，捕获不到的异常就是404了
 */
@Controller
public class NotFoundException implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = {"/error"})
    @ResponseBody
    public Object error(HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();

        String requestHeaderType = request.getHeader("type");
        if(!StringUtils.isBlank(requestHeaderType)){
            body.put("error", "Header's field [type] is not defined,your input is " + requestHeaderType);
            body.put("code", "404-1");
        }else {
            body.put("error", "not found");
            body.put("code", "404");
            body.put("reason maybe in", Arrays.asList("Resources does not exist","Request url does not exist","Header's field [type] does not exist"));
        }

        return body;
    }
}
