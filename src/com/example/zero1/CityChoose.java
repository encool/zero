package com.example.zero1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.zero1.db.DBmanager;

public class CityChoose extends Activity implements TextWatcher{
	final static int QUERY_RESULT_CODE=1;
	CityAdapter hotcityadpter;
	CityAdapter recentcityadpter;
	CityAdapter queryresultadapter;
	GridView queryresultgdview;
	GridView recentlyview;
	GridView hotcityview;
	View bottomview;
	EditText serchtext;
	ArrayList<Station> hotstation;
	ArrayList<Station> recentstation;
	ArrayList<Station> queriedstation=new ArrayList<Station>();
	DBmanager dbmanager;
	int[] b={11,211,311,4111}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.city_choose_main);
		super.onCreate(savedInstanceState);
		dbmanager=new DBmanager(this);
		hotstation=dbmanager.getHotStaions();
		recentstation=dbmanager.getRecentlyStation();
		hotcityadpter =new CityAdapter(hotstation,this);
		recentcityadpter =new CityAdapter(recentstation,this);
		queryresultadapter= new CityAdapter(queriedstation,this);
		hotcityview=(GridView)findViewById(R.id.hotgd);
		recentlyview=(GridView)findViewById(R.id.recentlygd);
		queryresultgdview=(GridView)findViewById(R.id.resultgridview);
		bottomview=findViewById(R.id.bottomview);
		queryresultgdview.setAdapter(queryresultadapter);
		queryresultgdview.setNumColumns(3);
		queryresultgdview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) view.findViewById(R.id.cityview);
				Station station=(Station) tv.getTag();
				String station_ch_name=station.station_name_ch;
				String station_code=station.station_code;
	            Intent data=new Intent();
	            data.putExtra("station_code", station_code);
	            data.putExtra("station_ch_name", station_ch_name);  
	            //请求代码可以自己设置
	            setResult(QUERY_RESULT_CODE,data);  
	            //关闭掉这个Activity  
	            dbmanager.addRecentlyStation(station);
	            finish(); 
			}
			
		});
		hotcityview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) view.findViewById(R.id.cityview);
				Station station=(Station) tv.getTag();
				String station_ch_name=station.station_name_ch;
				String station_code=station.station_code;
	            Intent data=new Intent();
	            data.putExtra("station_code", station_code);
	            data.putExtra("station_ch_name", station_ch_name);  
	            //请求代码可以自己设置
	            setResult(QUERY_RESULT_CODE,data);  
	            //关闭掉这个Activity  
	            dbmanager.addRecentlyStation(station);
	            finish(); 
			}
			
		});
		recentlyview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) view.findViewById(R.id.cityview);
				Station station=(Station) tv.getTag();
				String station_ch_name=station.station_name_ch;
				String station_code=station.station_code;
	            Intent data=new Intent();
	            data.putExtra("station_code", station_code);
	            data.putExtra("station_ch_name", station_ch_name);  
	            //请求代码可以自己设置
	            setResult(QUERY_RESULT_CODE,data);  
	            //关闭掉这个Activity  
	            dbmanager.addRecentlyStation(station);
	            finish(); 
			}
			
		});

		hotcityview.setAdapter(hotcityadpter);
		hotcityview.setNumColumns(3);
		recentlyview.setAdapter(recentcityadpter);
		recentlyview.setNumColumns(3);
		queryresultgdview.setVisibility(View.INVISIBLE);
		serchtext=(EditText) findViewById(R.id.username);
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
//			String ss=Utility.prepareString(s.toString());
//			Log.i("fuck after", ss);
		
		}
		if(s.length()==0){
			bottomview.setVisibility(View.VISIBLE);
			queryresultgdview.setVisibility(View.INVISIBLE);
		}else{
//			queryresultadapter.stations.clear();
//			queryresultadapter.notifyDataSetChanged();
			ArrayList<Station> stations=dbmanager.queryStationFromdb(20, s.toString());
			queryresultadapter.setStationsAndRefresh(stations);
//			queryresultadapter.notifyDataSetChanged();
		}

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbmanager.closeBD();
	}

}
