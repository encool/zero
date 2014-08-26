package com.example.zero1;

import java.util.ArrayList;

import com.example.zero1.account.User;

public class OrderManager {
	public OrderManager(TicketClient client, User user) {
		super();
		this.client = client;
		this.user = user;
	}
	TicketClient client;
	User user;
	public ArrayList<Order> getNoCompleteOrders(){
		if(!user.isIsauthed()){
			return null;
		}
		return client.queryNocompleteOrder(user);
	}
	public ArrayList<Order> getOrders(){
		if(!user.isIsauthed()){
			return null;
		}
		return client.queryMyOrder(user, null, null, null, null);
		
	}
	public boolean isUserLogined(){
		if(user==null)
			return false;
		return user.isIsauthed();
	}
}
