package com.leap.mars.presenter.auth.entity;

public class TuLingBackMessage {

	private int code;
	private String text;
	private String url;

	public TuLingBackMessage() {
		super();
	}

	public TuLingBackMessage(int code, String text, String url) {
		super();
		this.code = code;
		this.text = text;
		this.url = url;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", text=" + text + ", url=" + url
				+ "]";
	}

}
