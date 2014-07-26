package com.example.zero1.account;

import java.util.ArrayList;
import java.util.Iterator;

public class AccountManager {
	public AccountManager() {
		super();
	}
	ArrayList<User> loginedusers=new ArrayList<User>();
	void doAuth(){
		
	}
	public boolean hasLoginedUser(){
		return false;	
	} 
	public User generateUser(){
		return new User();
	}
	public User getLoginedUser(String username){
		if(hasLoginedUser()){
			if(username==null){
				return loginedusers.get(0);
			}
			Iterator<User> iterator =loginedusers.iterator();
			while(iterator.hasNext()){
				User user=iterator.next();
				if(user.name.equals(username)){
					return user;
//					break;
				}
			}			
		}
		return null;

		
//		return return loginedusers.;
	}
	public void setLoginedUser(User user){
		loginedusers.add(user);
	}
}
