package com.example.zero1;

import java.util.ArrayList;

import com.example.zero1.db.DBmanager;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

public class CityChoose extends Activity implements TextWatcher{
	
	CityAdapter hotcityadpter;
	CityAdapter recentcityadpter;
	CityAdapter queryresultadapter;
	GridView queryresultgdview;
	View bottomview;
	EditText serchtext;
	ArrayList<Station> hotstation;
	ArrayList<Station> recentstation;
	DBmanager dbmanager;
	int[] b={11,211,311,4111}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.city_choose_main);
		super.onCreate(savedInstanceState);
		dbmanager=new DBmanager(this);
		hotstation=dbmanager.queryStationFromdb(10,"ld");
		recentstation=dbmanager.queryStationFromdb(15,"ls");
		hotcityadpter =new CityAdapter(hotstation,this);
		recentcityadpter =new CityAdapter(recentstation,this);
		queryresultadapter= new CityAdapter(recentstation,this);
		GridView gdview=(GridView)findViewById(R.id.hotgd);
		GridView rcgdview=(GridView)findViewById(R.id.recentlygd);
		queryresultgdview=(GridView)findViewById(R.id.resultgridview);
		bottomview=findViewById(R.id.bottomview);
		queryresultgdview.setAdapter(queryresultadapter);
		queryresultgdview.setNumColumns(3);
		gdview.setAdapter(hotcityadpter);
		gdview.setNumColumns(3);
		rcgdview.setAdapter(recentcityadpter);
		rcgdview.setNumColumns(3);
		queryresultgdview.setVisibility(View.INVISIBLE);
		serchtext=(EditText) findViewById(R.id.editText1);
		serchtext.addTextChangedListener(this);
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		// TODO Auto-generated method stub
		if(s!=null&&(bottomview.getVisibility()==View.VISIBLE)){
			bottomview.setVisibility(View.INVISIBLE);
			queryresultgdview.setVisibility(View.VISIBLE);
			
		}
		
		if(s.length()==0){
			bottomview.setVisibility(View.VISIBLE);
			queryresultgdview.setVisibility(View.INVISIBLE);
		}
	}

}
