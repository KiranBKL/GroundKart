package com.springrest.exception;

import java.time.LocalDateTime;

public class ErrorInfo {
	
	LocalDateTime timeStamp;
	String msg;
	String uri;
	
	public ErrorInfo()
	{
		
	}

	public ErrorInfo(LocalDateTime timeStamp, String msg, String uri) {
		super();
		this.timeStamp = timeStamp;
		this.msg = msg;
		this.uri = uri;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "ErrorInfo [timeStamp=" + timeStamp + ", msg=" + msg + ", uri=" + uri + "]";
	}
	
	

}
