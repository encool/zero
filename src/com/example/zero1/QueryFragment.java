package com.example.zero1;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QueryFragment extends Fragment {
	
	final static int LEFTSTATION_REQUEST_CODE=1;
	final static int RIGHTSTATION_REQUEST_CODE=2;
	TextView leftstationview;
	TextView rightstationview;
	View data_select_view;
	
	Button querybtn;
	Button querystubutton;
	Date date;
	TextView dateview;
	TextView timeview;
	
	Activity activity;
	
	SimpleDateFormat dfforquery=new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
	SimpleDateFormat dfweek=new SimpleDateFormat("EEEE");
	
	TextView weekview;
	public QueryFragment() {
		// TODO Auto-generated constructor stub
	}

//	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootview=inflater.inflate(R.layout.fragment_query_main, container,false);	
		return rootview;
	}
	void setListener(){
		LinearLayout from_station=(LinearLayout) activity.findViewById(R.id.itemview_from_station);
		from_station.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(activity,CityChoose.class), LEFTSTATION_REQUEST_CODE);
			}});
		LinearLayout to_station=(LinearLayout) activity.findViewById(R.id.to_station);
		to_station.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(activity,CityChoose.class), RIGHTSTATION_REQUEST_CODE);
			}});
		data_select_view=activity.findViewById(R.id.data_select_btn);
		data_select_view.setOnClickListener(new OnClickListener(){

			private AlertDialog dateselectDialog;

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Builder builder = new AlertDialog.Builder(activity);
//				LayoutInflater inflater = LayoutInflater.from(activity);
//				View layout = inflater.inflate(R.layout.timeintervalselect, null);
				LayoutParams param=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				LinearLayout linearlayout=new LinearLayout(activity);
				CalendarView calendar=new CalendarView(activity);
				param.height=500;
				linearlayout.setBackgroundColor(Color.GRAY);
				linearlayout.setPadding(5, 5, 5, 5);
				calendar.setShownWeekCount(6);
				calendar.setShowWeekNumber(false);
				calendar.setBackgroundColor(Color.WHITE);
				calendar.setDate(date.getTime());
				linearlayout.addView(calendar, param);
		        builder.setView(linearlayout);
		        dateselectDialog = builder.create();
				calendar.setOnDateChangeListener(new OnDateChangeListener(){

					@Override
					public void onSelectedDayChange(CalendarView calendar,
							int arg1, int arg2, int arg3) {
						// TODO Auto-generated method stub
						long l=calendar.getDate();
						date=new Date(l);
						String s=df.format(date);
						dateview.setText(s);
						dateview.setTag(date);
						weekview.setText(dfweek.format(date));
						dateselectDialog.dismiss();
					}});
				dateselectDialog.show();
			}});
		querybtn.setOnClickListener(new OnClickListener(){

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle(); 
				bundle.putString("date", dfforquery.format(dateview.getTag()));
				bundle.putString("to_station", (String)rightstationview.getTag());
				bundle.putString("from_station",(String)leftstationview.getTag()); 
				activity.startActivity(new Intent(activity, TrainsInfoActivity.class).putExtra("traininfo", bundle));
			}}); 
	}

	void setView(){
		leftstationview=(TextView) activity.findViewById(R.id.leftstation);
		rightstationview=(TextView) activity.findViewById(R.id.rightstation);
		//根据当前时间初始话这两个view
		dateview=(TextView) activity.findViewById(R.id.date);
		date=new Date();
		dateview.setText(df.format(date));	
		weekview=(TextView) activity.findViewById(R.id.week);
		weekview.setText(dfweek.format(date));
		
		querybtn=(Button) activity.findViewById(R.id.query_ordinary_btn);
		querystubutton=(Button) activity.findViewById(R.id.query_stu_btn);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity=getActivity();
		setView();
		setListener();
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data); 
		if(requestCode==LEFTSTATION_REQUEST_CODE&&resultCode==CityChoose.QUERY_RESULT_CODE){
			leftstationview.setText(data.getExtras().getString("station_ch_name"));
			leftstationview.setTag(data.getExtras().getString("station_code"));
		}
		if(requestCode==RIGHTSTATION_REQUEST_CODE&&resultCode==CityChoose.QUERY_RESULT_CODE){
			rightstationview.setText(data.getExtras().getString("station_ch_name"));
			rightstationview.setTag(data.getExtras().getString("station_code"));
		}
	}
}
