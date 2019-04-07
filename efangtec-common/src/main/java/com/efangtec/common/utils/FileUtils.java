package com.efangtec.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.*;
import java.util.Date;

/**
 * Created by Administrator on 2017-12-13.
 */
public class FileUtils {

    private static long appId = 1252783289;
    private static String secretId = "AKID88vX1KuuRr6FTZpQIeBewMdjsreFktw5";
    private static String secretKey = "CG9qnY8btzudFjUhLvJkz7OfolW7Wkpo";
    private static String bucketName = "ec-2017";

    public static COSClient getCOSClient() {
       // PoolingHttpClientConnectionManager s;
        Credentials cred = new Credentials(appId, secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion("bj"); // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        COSClient cosClient = null;
        try {
             cosClient = new COSClient(clientConfig, cred);

        }catch (Exception e){
            e.printStackTrace();
        }
        return cosClient;
    }

    public static String uploadFile(String remoteFileName, byte[] localFileByte) {
        String access_url = "";
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, remoteFileName, localFileByte);
        String uploadFileRet = getCOSClient().uploadFile(uploadFileRequest);
        JSONObject jsonObject = JSON.parseObject(uploadFileRet);
        if (jsonObject.getIntValue("code") == 0) {
            access_url = jsonObject.getJSONObject("data").getString("source_url");
        }
        return access_url;
    }
//{"code":-3,"message":"server response is not json, httpRequest: url:http://bj2.file.myqcloud.com/files/v2/1252783289/ec-2017/2018/demo1.jpg, method:POST, ConentType:multipart/form-data\nHeaders:\nkey:Authorization, value:3Sm2l9wlZGnu2Z2RqduDIaztiF1hPTEyNTI3ODMyODkmaz1BS0lEODh2WDFLdXVScjZGVFpwUUllQmV3TWRqc3JlRmt0dzUmZT0xNTEzMTU0NjI5JnQ9MTUxMzE1NDMyOSZyPTkzMzA2MDgwMiZmPS8xMjUyNzgzMjg5L2VjLTIwMTcvMjAxOC9kZW1vMS5qcGcmYj1lYy0yMDE3\nkey:User-Agent, value:cos-java-sdk-v4.2\nparams:\nkey:op, value:upload\nkey:sha, value:459b12beacdc43ced5100bb51a63a44c25316ee5\nkey:biz_attr, value:\nkey:insertOnly, value:1\n, httpResponse: HttpResponseProxy{HTTP/1.1 403 Forbidden [Server: NWS_SP, Connection: keep-alive, Date: Wed, 13 Dec 2017 08:38:54 GMT, Content-Type: text/html, Content-Length: 101] ResponseEntityProxy{[Content-Type: text/html,Content-Length: 101,Chunked: false]}}, responseStr: You do not have permission to get URL '/files/v2/1252783289/ec-2017/2018/demo1.jpg' from this server."}
//{"code":-133,"message":"ERROR_CMD_BUCKET_NOTEXIST","request_id":"NWEzMGU3NWJfYWRhZDM1MGFfMTZhOGRfMTY2YTMx"}
//{"code":0,"message":"SUCCESS","request_id":"NWEzMGU3NzlfNGMyODVkNjRfMjZiMTZfNjhh","data":{"access_url":"http://ec-2017-1252783289.file.myqcloud.com/2018/demo19.jpg","resource_path":"/1252783289/ec-2017/2018/demo19.jpg","source_url":"http://ec-2017-1252783289.cosbj.myqcloud.com/2018/demo19.jpg","url":"http://bj.file.myqcloud.com/files/v2/1252783289/ec-2017/2018/demo19.jpg","vid":"7d90d45ca6e47240c073a848c5884a241513154425"}}

    public static String uploadFile() {
        String remoteFileName  = DateFormatUtils.format(new Date(),"/yyyy/MM/dd")+"/"+System.nanoTime()+".jpg";
      //  UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, "/2018/demo19.jpg", "F:\\ppt.jpg");
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, remoteFileName, "C:\\Users\\Admin\\Desktop\\Desert.jpg");
        String uploadFileRet = getCOSClient().uploadFile(uploadFileRequest);
        return uploadFileRet;
    }
    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    public static void main(String[] arg) {
        String json = uploadFile();
        System.out.println(json);
        /*
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject.getIntValue("code"));
        System.out.println(jsonObject.getJSONObject("data").getString("access_url"));*/
       String s = "11111111.jgp";
       System.out.println(StringUtils.substringAfter(s,"."));
    }
}
