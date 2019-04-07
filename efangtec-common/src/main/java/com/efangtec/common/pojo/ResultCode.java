package com.efangtec.common.pojo;

public class ResultCode implements  java.io.Serializable {
	
	public String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String message;

	public ResultCode(){

	}


	private ResultCode(String code,String message){
		this.code = code;
		this.message = message;
	}
	public static final ResultCode PALY_ERROR = new ResultCode("E10002", "no play");

	public static final ResultCode REGISTER_NEXT = new ResultCode("S10002", "register_next");

	public static final ResultCode SUCCESS = new ResultCode("200", "success");

	public static final ResultCode OPENID_ERROR = new ResultCode("E000010","open is empty");

    public static final ResultCode SYSTEM_ERROR = new ResultCode("E00001","system error");

    public static final ResultCode IMAGE_FORMAT_ERROR = new ResultCode("E00002","image format not valid");

    public static final ResultCode FILE_EXISTS_ERROR = new ResultCode("E00003","file is exists");

    public static final ResultCode PARAMETER_ERROR = new ResultCode("E00004","parameter error");

    public static final ResultCode FILE_NOT_EXISTS_ERROR = new ResultCode("E00005","file not exists");

    public static final ResultCode VALIDATE_FAILURE = new ResultCode("E00006","validate failure");//验证失败

    public static final ResultCode NOT_LOGIN = new ResultCode("E00007","not login");

    public static final ResultCode USER_EXISTED = new ResultCode("E00008","phone has been registered");

    public static final ResultCode TIMES_EXCEED = new ResultCode("E00009","try times has been exceed 3");

	public static final ResultCode AUTHENTICATION = new ResultCode("E00401","请登录");

	public static final ResultCode ACCESS_DENIED = new ResultCode("E00403","没有改接口的访问权限");

	public static final ResultCode UNCOMPLETED = new ResultCode("E00010","uncompleted");//资料未完成


}
