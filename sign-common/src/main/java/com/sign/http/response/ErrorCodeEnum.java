package com.sign.http.response;

public enum ErrorCodeEnum {
	//系统类错误1000开始编号（1000-1999）
	SUCCESS(1000,"成功","success"),
	SystemError(1001,"系统错误","system error"),
	UploadFileEmpty(1002,"没有选择待上传的文件","Upload File is empty"),
	UploadFileTypeError(1003,"上传文件类型错误","Upload File Type error"),
	//用户类错误20000开始编号（2000-2999）
	UserNameOrPasswordError(2000,"用户名或密码错误","username or password error");
	
	//账户类错误3000开始编号（3000-3999）
	
	//支付类错误4000开始编号（4000-4999）
	
	//charging类错误5000开始编号（5000-5999）
	
	//cfca类错误6000开始编号（6000-6999）
	
	//customer类错误7000开始编号（7000-7999）
	
	//file类错误8000开始编号 （8000-8999）
	
	//notice类错误9000开始编号（9000-9999）
	
	//authentication错误10000开始编号（范围：10000-10999）
	
	//batch类错误11000开始编号（范围：11000-11999）
	
	public int respCode;
	public String explainCn;
	public String explainEn;
	private ErrorCodeEnum(int respCode,String explainCn,String explainEn) {
		this.respCode = respCode;
		this.explainCn = explainCn;
		this.explainEn = explainEn;
	}
	
	public static ErrorCodeEnum valueOf(int respCode) {
		ErrorCodeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
        	ErrorCodeEnum rsCode = var1[var3];
            if (rsCode.respCode==respCode) {
                return rsCode;
            }
        }

        return null;
    }
}
