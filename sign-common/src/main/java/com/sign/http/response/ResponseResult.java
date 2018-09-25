package com.sign.http.response;

/**
 * 返回结果对象
 * @author win7
 *
 */
public class ResponseResult {
	private int code;//结果code
	private String msg;//结果内容
	public ResponseResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
