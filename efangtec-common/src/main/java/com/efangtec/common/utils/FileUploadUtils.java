package com.efangtec.common.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2018-09-05.
 */
public class FileUploadUtils {

    /**
     * 获取文件md5值
     * @param file
     * @return
     */
    public static String getMD5(File file) {
        InputStream in = null;
       try{
           in = new FileInputStream(file);
           MessageDigest digest = MessageDigest.getInstance("MD5");  ;
           byte buffer[] = new byte[8192];
           int len;
           while((len = in.read(buffer))!=-1){
               digest.update(buffer, 0, len);
           }
           return new String(Hex.encodeHex(digest.digest()));
       }catch (Exception e){
           e.printStackTrace();
           return "-1";
       } finally {
           if (in != null) {
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
    }

    /**
     * 获取文件格式
     * @param is
     * @return
     * @throws IOException
     */
    public static FileType getFileType(InputStream is) throws IOException {
        byte[] src = new byte[28];
        is.read(src, 0, 28);
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        FileType[] fileTypes = FileType.values();
        for (FileType fileType : fileTypes) {
            if (stringBuilder.toString().startsWith(fileType.getValue())) {
                return fileType;
            }
        }
        return null;
    }

}
