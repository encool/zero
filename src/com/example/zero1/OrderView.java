package com.example.zero1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderView extends LinearLayout {
	Order order;
	
	TextView startstation;
	TextView arrivestaion;
	TextView ordernum;
	TextView passangername;
	TextView date;
	TextView time;
	TextView seattype;
	TextView seatnum;
	
	public OrderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.orderview_layout, this);
	}

	public OrderView(Context context) {
//		super(context);
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	void initView(){
		startstation=(TextView) this.findViewById(R.id.startstation);
		arrivestaion=(TextView) this.findViewById(R.id.arrivestaion);
		ordernum=(TextView) this.findViewById(R.id.ordernum);
		passangername=(TextView) this.findViewById(R.id.passangername);
		startstation=(TextView) this.findViewById(R.id.startstation);
		date=(TextView) this.findViewById(R.id.date);
		time=(TextView) this.findViewById(R.id.time);
		seattype=(TextView) this.findViewById(R.id.seattype);
		seatnum=(TextView) this.findViewById(R.id.seatnum);
		
	}

	public void setOrderinfo(Order order) {
		this.order = order;
	}

}
