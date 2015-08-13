package com.superdroid.googleplay.entities;

public class UserInfo {
	private String userName;
	private String email;
	private String imgUrl;

	public UserInfo(String userName, String email, String imgUrl) {
		super();
		this.userName = userName;
		this.email = email;
		this.imgUrl = imgUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
