package com.netease.ar.common.model.user;

import java.io.Serializable;
import java.util.Date;

public class UserModel implements Serializable {

	private long userId;
	private long userNum;
	private String nick;
	private String avatar;
	private int status;
	private int wealthLevel;
	private int wealthScore;
	private String mail;
	private String phone;
	private Date createdTime;

	public long getUserNum() {
		return userNum;
	}

	public void setUserNum(long userNum) {
		this.userNum = userNum;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWealthLevel() {
		return wealthLevel;
	}

	public void setWealthLevel(int wealthLevel) {
		this.wealthLevel = wealthLevel;
	}

	public int getWealthScore() {
		return wealthScore;
	}

	public void setWealthScore(int wealthScore) {
		this.wealthScore = wealthScore;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
