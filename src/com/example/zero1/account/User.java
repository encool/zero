package com.example.zero1.account;

public class User {
	public User(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}
	public User() {
		// TODO Auto-generated constructor stub
		isauthed=false;
	}
	boolean isauthed;
	String identity;
	String cookie;
	String name;
	String pwd;
	String passcode;
	public boolean isIsauthed() {
		return isauthed;
	}
	public void setIsauthed(boolean isauthed) {
		this.isauthed = isauthed;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String certificate) {
		this.identity = certificate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}
