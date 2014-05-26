package com.example.zero1.account;

public class User {
	public User(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}
	boolean isauthed;
	String certificate;
	String name;
	String pwd;
	public boolean isIsauthed() {
		return isauthed;
	}
	public void setIsauthed(boolean isauthed) {
		this.isauthed = isauthed;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
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
	
}
