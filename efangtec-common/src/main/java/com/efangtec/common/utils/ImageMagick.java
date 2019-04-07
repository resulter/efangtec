package com.efangtec.common.utils;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-03.
 */
public class ImageMagick {

    public static String imageMagickPath = null;
    static {
        System.out.println("init env ");
        //imageMagickPath = "D:\\GraphicsMagick-1.3.23-Q16";
    }

    public static int getWidth(String imagePath)
    {
        int line = 0;
        try {
            IMOperation op = new IMOperation();

            op.format("%w"); // 设置获取宽度参数
            op.addImage(1);
            IdentifyCmd identifyCmd = new IdentifyCmd(true);
           // identifyCmd.setSearchPath(imageMagickPath);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identifyCmd.setOutputConsumer(output);
            identifyCmd.run(op, imagePath);
            ArrayList<String> cmdOutput = output.getOutput();
            assert cmdOutput.size() == 1;
            line = Integer.parseInt(cmdOutput.get(0));
        } catch (Exception e) {
            line = 0;
            e.printStackTrace();
        }
        return line;
    }

    // -density 288
    public static boolean zoomImage(String imagePath, String newPath, Integer width, Integer height)
    {

        boolean flag = false;
        try {
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            if (width != null && getWidth(imagePath) < width) {
                width = getWidth(imagePath);
            }
            op.quality(75.00);
            if (width == null) {// 根据高度缩放图片
                op.resize(null, height, '^').strip().antialias().enhance().unsharp(5.0);
            } else if (height == null) {// 根据宽度缩放图片
                op.resize(width, null, '^').strip().antialias().enhance().unsharp(5.0);
            } else {
                op.resize(width, height, '^').strip().antialias().enhance().unsharp(5.0);
            }
            //op.addRawArgs("-density", "100");
            //op.density(100, 100);
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd(true);
           // convert.setSearchPath(imageMagickPath);
            convert.run(op);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;

        } finally {

        }
        return flag;
    }

    public static void main(String[] args) throws Exception
    {

        zoomImage("E:\\var\\2018\\09\\03\\28907439379651.jpg", "E:\\var\\2018\\09\\03\\28907439379651.jpg", 500, null);
    }

}
