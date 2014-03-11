package com.example.zero1;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

public class CityChoose extends Activity implements TextWatcher{
	
	CityAdapter hotadpter;
	CityAdapter rcadpter;
	CityAdapter rsadapter;
	GridView rsgdview;
	View bottomview;
	EditText serchtext;
	int[] a={1,2,3,4};
	int[] b={11,211,311,4111};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.city_choose_main);
		super.onCreate(savedInstanceState);
		hotadpter =new CityAdapter(a,this);
		rcadpter =new CityAdapter(a,this);
		rsadapter= new CityAdapter(b,this);
		GridView gdview=(GridView)findViewById(R.id.hotgd);
		GridView rcgdview=(GridView)findViewById(R.id.recentlygd);
		rsgdview=(GridView)findViewById(R.id.resultgridview);
		bottomview=findViewById(R.id.bottomview);
		rsgdview.setAdapter(rsadapter);
		rsgdview.setNumColumns(3);
		gdview.setAdapter(hotadpter);
		gdview.setNumColumns(3);
		rcgdview.setAdapter(rcadpter);
		rcgdview.setNumColumns(3);
		rsgdview.setVisibility(View.INVISIBLE);
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
			rsgdview.setVisibility(View.VISIBLE);
			
		}
		
		if(s.length()==0){
			bottomview.setVisibility(View.VISIBLE);
			rsgdview.setVisibility(View.INVISIBLE);
		}
	}

}
