package com.example.zero1;

import com.example.zero1.account.User;

public class OrderManager {
	public OrderManager(TicketClient client, User user) {
		super();
		this.client = client;
		this.user = user;
	}
	TicketClient client;
	User user;
	public Order[] getNoCompleteOrders(){
		if(!user.isIsauthed()){
			return null;
		}
		return client.queryNocompleteOrder();
	}
	public Order[] getOrders(){
		return null;
		
	}
}
