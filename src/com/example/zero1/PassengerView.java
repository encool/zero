package com.example.zero1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class PassengerView extends LinearLayout {
	
	TextView passengername;
	TextView idnum;
	TextView status;
	public PassengerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.passenger_view_layout, this);
		this.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		initView();
	}
	public PassengerView(Context context){
		this(context, null);
	}
	public void initView(){
		passengername=(TextView) this.findViewById(R.id.passangername);
		idnum=(TextView) this.findViewById(R.id.idnum);
		status=(TextView) this.findViewById(R.id.status);
	}
	public void setPassengerInfo(Passenger p){
		passengername.setText(p.name);
		idnum.setText(p.id);
		status.setText(p.phonenum);
	}
}
