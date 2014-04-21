package com.example.zero1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QueryFragment extends Fragment {
	
	final static int LEFTSTATION_REQUEST_CODE=1;
	final static int RIGHTSTATION_REQUEST_CODE=2;
	TextView leftstationview;
	TextView rightstationview;
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
		LinearLayout from_station=(LinearLayout) getActivity().findViewById(R.id.from_station);
		from_station.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(getActivity(),CityChoose.class), LEFTSTATION_REQUEST_CODE);
			}});
		LinearLayout to_station=(LinearLayout) getActivity().findViewById(R.id.to_station);
		to_station.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(getActivity(),CityChoose.class), RIGHTSTATION_REQUEST_CODE);
			}});
	}

	void setView(){
		leftstationview=(TextView) getActivity().findViewById(R.id.leftstation);
		rightstationview=(TextView) getActivity().findViewById(R.id.rightstation);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setListener();
		setView();
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
			leftstationview.setTag(data.getExtras().getString("station_code"));
		}
	}
}
