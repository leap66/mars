package com.leap.mars.presenter.auth.entity;

import android.graphics.Bitmap;

public class RobotInfo {
	private String nickname;
	private String name;
	private String birthDay;
	private String bitmapPath;
	private String signature;
	private String grade;
	private String hobby;
	private String phone;
	private String tel;
	private String address;
	private String QQ;
	private String weixin;
	private String notes;
	private int age;
	private Bitmap bitmap;

	public RobotInfo() {
		super();
	}

	public RobotInfo(String nickname, String name, String birthDay,
			String bitmapPath, String signature, String grade, String hobby,
			String phone, String tel, String address, String qQ, String weixin,
			String notes, int age, Bitmap bitmap) {
		super();
		this.nickname = nickname;
		this.name = name;
		this.birthDay = birthDay;
		this.bitmapPath = bitmapPath;
		this.signature = signature;
		this.grade = grade;
		this.hobby = hobby;
		this.phone = phone;
		this.tel = tel;
		this.address = address;
		QQ = qQ;
		this.weixin = weixin;
		this.notes = notes;
		this.age = age;
		this.bitmap = bitmap;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getBitmapPath() {
		return bitmapPath;
	}

	public void setBitmapPath(String bitmapPath) {
		this.bitmapPath = bitmapPath;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "RobotInfo [nickname=" + nickname + ", name=" + name
				+ ", birthDay=" + birthDay + ", bitmapPath=" + bitmapPath
				+ ", signature=" + signature + ", grade=" + grade + ", hobby="
				+ hobby + ", phone=" + phone + ", tel=" + tel + ", address="
				+ address + ", QQ=" + QQ + ", weixin=" + weixin + ", notes="
				+ notes + ", age=" + age + ", bitmap=" + bitmap + "]";
	}
}
