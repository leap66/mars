package com.leap.mars.presenter.auth.entity;

public class RecorderVoice {

	/*
	 * ������Ϣ������
	 */
	String name;
	/**
	 * ������Ϣ����
	 */
	String content;
	/**
	 * ����������Ϣ��ʱ��
	 */
	String date;
	/**
	 * ������Ϣ�����
	 */
	Type type;

	public enum Type {
		INPUT, OUTPUT;
	}

	/**
	 * ������Ϣ��ʱ��
	 */
	private float time;

	/**
	 * ������Ϣ��·��
	 */
	private String filePath;

	/**
	 * ������Ϣ�ĳ�����·��
	 */
	private String URLPath;

	public RecorderVoice() {
		super();
	}

	public RecorderVoice(String name, String content, String date, Type type,
			float time, String filePath, String uRLPath) {
		super();
		this.name = name;
		this.content = content;
		this.date = date;
		this.type = type;
		this.time = time;
		this.filePath = filePath;
		URLPath = uRLPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getURLPath() {
		return URLPath;
	}

	public void setURLPath(String uRLPath) {
		URLPath = uRLPath;
	}

	@Override
	public String toString() {
		return "RecorderVoice [name=" + name + ", content=" + content
				+ ", date=" + date + ", type=" + type + ", time=" + time
				+ ", filePath=" + filePath + ", URLPath=" + URLPath + "]";
	}

}
