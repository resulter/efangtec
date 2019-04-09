package com.efangtec.common.pojo;

/**
 * 常量
 *
 */
public final class Constants {

    public static String DRUGS_START_STR = "ef-";



    //删除标志：已删除
    public static int DELETE_YES = -1;
    //删除标志：未删除
    public static int DELETE_NO = 1;

    //部门是否是最低级标志。即是否为代表 ：否
    public static int LEVEL_LAST_NO = -1;
    //部门是否是最低级标志。即是否为代表 ：是
    public static int LEVEL_LAST_YES = 1;

    //用户锁定状态 正常
    public static  int LOCKED_STATE_NORMAL = 1;
    //用户锁定状态 不正常
    public static  int LOCKED_STATE_ABNORMAL = -1;
    //消息-已读
    public static int READING_SATUS_YES = 1;
    //消息-未读
    public static int READING_SATUS_NO = -1;
    //是否是负责人 是
    public static  int IS_BOSS_YES = 1;
    //是否是负责人 不是
    public  static  int IS_BOSS_NO = -1;
    //站内消息类型 战报
    public static  String MESSAGE_TYPE_REPORT = "1";
    //站内消息类型 会议跟进
    public  static  String  MESSAGE_TYPE_FOLLOW= "2";
    //手机端我的团队item类型 代表
    public  static  String ITEM_TYPE_EMP = "emp";
    //手机端我的团队item类型 部门
    public  static  String ITEM_TYPE_DEPT = "dept";


}
