package com.leap.mars.presenter.auth.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ItemInfo implements Serializable {

	/*
	 * ��Ϣ������
	 */
	String name;
	/**
	 * ��Ϣ����
	 */
	String content;
	/**
	 * ������Ϣ��ʱ��
	 */
	String date;
	/**
	 * ��Ϣ�����
	 */
	Type type;

	public enum Type {
		INPUT, OUTPUT;
	}

	public ItemInfo() {
		super();
	}

	public ItemInfo(String name, String content, String date, Type type) {
		super();
		this.name = name;
		this.content = content;
		this.date = date;
		this.type = type;
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

	@Override
	public String toString() {
		return "ItemInfo [name=" + name + ", content=" + content + ", date="
				+ date + ", type=" + type + "]";
	}
}
