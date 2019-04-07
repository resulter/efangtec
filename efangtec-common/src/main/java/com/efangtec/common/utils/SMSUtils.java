package com.efangtec.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 发送短息
 */
@Component
public class SMSUtils {
    private Logger logger = LoggerFactory.getLogger(MessageUtils.class);
    @Value("${efangtec.sendMessageUrl}")
    private String sendMessageUrl;
    @Value("${efangtec.chanzorCount}")
    private String chanzorCount;
    @Value("${efangtec.chanzorPassword}")
    private String chanzorPassword;

    //创建固定大小为100 的线程池
    private static ExecutorService service = Executors.newFixedThreadPool(100);

    /**
     * 异步发送短信通知
     *
     * @param content 内容
     * @param phone   接收人信息
     */
    public Future<String> sendMessage(String content, String phone) {
        System.out.println("phone:" + phone);
        System.out.println("content:" + content);
        //短信内容
        //String content = StringUtils.replace(messageTemplate, "${name}", data.getName());
        //发送短信参数
        try{



        StringBuffer param = new StringBuffer();
        param.append("account=" + chanzorCount).append("&password=" + chanzorPassword).append("&mobile=" + phone).append("&content=" + new String(content.getBytes("utf-8"),"utf-8"));
        //异步发送消息
        System.out.println("param:"+param.toString());
        String s = HttpSendUtils.sendPost(sendMessageUrl, param.toString());
        System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
