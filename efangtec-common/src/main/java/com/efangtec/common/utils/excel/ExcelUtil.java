package com.efangtec.common.utils.excel;


import com.efangtec.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;


/**
 * <p>Title: ExcelVOAttribute.java</p>
 *
 * <p>Description:  * ExcelUtil工具类实现功能:
 * <p>
 * 导出时传入list<t>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 * <p>
 * 导入时读取excel,得到的结果是一个list<t>.T是自己定义的对象.
 * <p>
 *
 * 默认四级标题，已#&分隔传入，如需修改样式修改对应style即可
 *      eg：一级标题#&二级标题#&三级标题#&四级标题
 *
 *
 * <p>Company: CZJ</p>
 *
 * @author 王亚飞
 * @version 1.0
 * @date 2019-1-3
 */
public class ExcelUtil<T> {

    Class<T> clazz;


    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> importExcel(String sheetName, InputStream input) {
        int maxCol = 0;
        List<T> list = new ArrayList<T>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet sheet = workbook.getSheet(sheetName);
            if (!sheetName.trim().equals("")) {
                sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
            }
            if (sheet == null) {
                sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
            }
            int rows = sheet.getPhysicalNumberOfRows();

            if (rows > 0) {// 有数据时才处理
                // Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
                List<Field> allFields = getMappedFiled(clazz, null);

                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.
                for (Field field : allFields) {
                    // 将有注解的field存放到map中.
                    if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                        ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
                        int col = getExcelCol(attr.column());// 获得列号
                        maxCol = Math.max(col, maxCol);
                        // System.out.println(col + "====" + field.getName());
                        field.setAccessible(true);// 设置类的私有字段属性可访问.
                        fieldsMap.put(col, field);
                    }
                }
                for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.
                    HSSFRow row = sheet.getRow(i);
                    // int cellNum = row.getPhysicalNumberOfCells();
                    // int cellNum = row.getLastCellNum();
                    int cellNum = maxCol;
                    T entity = null;
                    for (int j = 0; j < cellNum; j++) {
                        HSSFCell cell = row.getCell(j);
                        if (cell == null) {
                            continue;
                        }
                        int cellType = cell.getCellType();
                        String c = "";
                        if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                            c = String.valueOf(cell.getNumericCellValue());
                        } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                            c = String.valueOf(cell.getBooleanCellValue());
                        } else {
                            c = cell.getStringCellValue();
                        }
                        if (c == null || c.equals("")) {
                            continue;
                        }
                        entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
                        // System.out.println(cells[j].getContents());
                        Field field = fieldsMap.get(j);// 从map中得到对应列的field.
                        if (field == null) {
                            continue;
                        }
                        // 取得类型,并根据对象类型设置值.
                        Class fieldType = field.getType();
                        if (String.class == fieldType) {
                            field.set(entity, String.valueOf(c));
                        } else if ((Integer.TYPE == fieldType)
                                || (Integer.class == fieldType)) {
                            field.set(entity, Integer.parseInt(c));
                        } else if ((Long.TYPE == fieldType)
                                || (Long.class == fieldType)) {
                            field.set(entity, Long.valueOf(c));
                        } else if ((Float.TYPE == fieldType)
                                || (Float.class == fieldType)) {
                            field.set(entity, Float.valueOf(c));
                        } else if ((Short.TYPE == fieldType)
                                || (Short.class == fieldType)) {
                            field.set(entity, Short.valueOf(c));
                        } else if ((Double.TYPE == fieldType)
                                || (Double.class == fieldType)) {
                            field.set(entity, Double.valueOf(c));
                        } else if (Character.TYPE == fieldType) {
                            if ((c != null) && (c.length() > 0)) {
                                field.set(entity, Character
                                        .valueOf(c.charAt(0)));
                            }
                        }

                    }
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param output java输出流
     */
    public HSSFWorkbook exportExcel(List<T> lists[], String sheetNames[],
                                    OutputStream output, String tableName) {
        if (lists.length != sheetNames.length) {
            System.out.println("数组长度不一致");
            return null;
        }

        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象

        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setBold(true);
        headfont.setFontHeightInPoints((short) 22);// 字体大小
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headstyle.setLocked(true);

        // 表头二级标题样式
        HSSFFont headfont2 = workbook.createFont();
        headfont2.setFontName("黑体");
        headfont2.setBold(true);
        headfont2.setFontHeightInPoints((short) 13);// 字体大小
        HSSFCellStyle headstyle2 = workbook.createCellStyle();
        headstyle2.setFont(headfont2);
        headstyle2.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headstyle2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headstyle2.setLocked(true);
        // 表头三级标题样式
        HSSFFont headfont3 = workbook.createFont();
        headfont3.setFontName("黑体");
        headfont3.setBold(true);
        headfont3.setFontHeightInPoints((short) 13);// 字体大小
        HSSFCellStyle headstyle3 = workbook.createCellStyle();
        headstyle3.setFont(headfont3);
        headstyle3.setLocked(true);

        // 正文样式
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setFontName("宋体");
        bodyFont.setFontHeightInPoints((short) 15);// 字体大小
        HSSFCellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        bodyStyle.setLocked(true);

        HSSFSheet sheet = workbook.createSheet();// 产生工作表对象

        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 18); //字体高度
        font.setColor(HSSFFont.COLOR_RED); //字体颜色
        font.setFontName("黑体"); //字体
        for (int ii = 0; ii < lists.length; ii++) {
            List<T> list = lists[ii];
            String sheetName = sheetNames[ii];

            List<Field> fields = getMappedFiled(clazz, null);
            String[] names = null;
            if(StringUtils.isNotBlank(tableName)) {
                names = tableName.split("#&");
            }
            int titleLines = StringUtils.isNotBlank(tableName)?names.length:0;
            if (names!=null&&names.length > 0 && StringUtils.isNotBlank(names[0])) {
                // 第一行表头标题
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fields.size()-1));
                HSSFRow row3 = sheet.createRow(0);
                row3.setHeight((short) 0x349);
                HSSFCell cell3 = row3.createCell(0);
                cell3.setCellStyle(headstyle);
                cell3.setCellValue(names[0]);
            }
            if (names!=null&&names.length > 1 && StringUtils.isNotBlank(names[1])) {
                // 第二行表头标题
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, fields.size()-1));
                HSSFRow row3 = sheet.createRow(1);
                row3.setHeight((short) 0x180);
                HSSFCell cell3 = row3.createCell(0);
                cell3.setCellStyle(headstyle2);
                cell3.setCellValue(names[1]);
            }
            if (names!=null&&names.length > 2 && StringUtils.isNotBlank(names[2])) {
                // 第二行表头标题
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, fields.size()-1));
                HSSFRow row3 = sheet.createRow(2);
                row3.setHeight((short) 0x150);
                HSSFCell cell3 = row3.createCell(0);
                cell3.setCellStyle(headstyle3);
                cell3.setCellValue(names[2]);
            }
            if (names!=null&&names.length > 3 && StringUtils.isNotBlank(names[3])) {
                // 第二行表头标题
                sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, fields.size()-1));
                HSSFRow row3 = sheet.createRow(3);
                row3.setHeight((short) 0x150);
                HSSFCell cell3 = row3.createCell(0);
                cell3.setCellStyle(headstyle3);
                cell3.setCellValue(names[3]);
            }
            workbook.setSheetName(ii, sheetName);


            HSSFRow row;
            HSSFCell cell;// 产生单元格
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillBackgroundColor(HSSFColor.LIME.index);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
            row = sheet.createRow(titleLines);// 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelVOAttribute attr = field
                        .getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名

                // 如果设置了提示信息则鼠标放上去提示.
                if (!attr.prompt().trim().equals("")) {
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0) {
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
                }
                //拿到palette颜色板
                HSSFPalette palette = workbook.getCustomPalette();
                //这个是重点，具体的就是把之前的颜色 HSSFColor.LIME.index
                //替换为  RGB(51,204,204) 宝石蓝这种颜色
                //你可以改为 RGB(0,255,127)
                palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 0, (byte) 255, (byte) 127);

                cell.setCellStyle(style);
            }

            int startNo = 0;
            int endNo = list.size();
            // 写入各条记录,每条记录对应excel表中的一行
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 + titleLines - startNo);
                row.setHeight((short) 0x200);
                T vo = (T) list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);// 获得field.
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);

                    try {
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellStyle(bodyStyle);
                            if (!attr.column().equals("A")) {
                            	cell.setCellValue(field.get(vo) == null ? ""
                                        : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                            } else {
                                cell.setCellValue(i + 1);// 如果数据存在就填入,不存在填入空格.
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (int i = 0; i < fields.size(); i++) {
                sheet.autoSizeColumn(i);
            }
        }

        try {
            if (output != null) {
                output.flush();
                workbook.write(output);
                output.close();
            }

            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Output is closed ");
            return null;
        }

    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     *                  每个sheet中数据的行数,此数值必须小于65536
     * @param output    java输出流
     */
    @SuppressWarnings("unchecked")
    public HSSFWorkbook exportExcel(List<T> list, String sheetName,
                                    OutputStream output, String tableName) {
        //此处 对类型进行转换
        List<T> ilist = new ArrayList<>();
        for (T t : list) {
            ilist.add(t);
        }
        List<T>[] lists = new ArrayList[1];
        lists[0] = ilist;

        String[] sheetNames = new String[1];
        sheetNames[0] = sheetName;

        return exportExcel(lists, sheetNames, output, tableName);
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param col
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
                                          String promptContent, int firstRow, int endRow, int firstCol,
                                          int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint
                .createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(
                regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
                                              String[] textlist, int firstRow, int endRow, int firstCol,
                                              int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint
                .createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(
                regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    /**
     * 得到实体类所有通过注解映射了数据表的字段
     *
     * @param
     * @return
     */
    @SuppressWarnings("rawtypes")
    private List<Field> getMappedFiled(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<Field>();
        }

        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), fields);
        }

        return fields;
    }


}