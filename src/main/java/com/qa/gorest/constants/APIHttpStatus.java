package com.qa.gorest.constants;

public enum APIHttpStatus {

	OK_200(200,"OK"),
	CREATED_201(201,"Created"),
	NO_CONTENT_204(204,"No Content"),
	BAD_REQUEST_400(400,"Bad Request"),
	UNAUTHORIZED_401(401,"Unauthorized"),
	NOT_FOUND_404(404,"Not Found"),
	INTERNAL_SERVER_ERROR_500(500,"Internal Server Error");
	
	
	private final int  code;
	private  final String msg;
	
	APIHttpStatus(int code, String msg) {
		this.code=code;
		this.msg=msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	



}
