package com.example.zero1;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TrainInfoListAdapter extends BaseAdapter {
	
	public TrainInfoListAdapter(Context context,
			ArrayList<TrainInfoHoder> trainarray) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.trainarray = trainarray;
	}
	
	boolean isupdated=false; //是否查询了，如果查询到的结果为空，提示查询失败，点击刷新按钮时 需要考虑重置问题。
	LayoutInflater inflater;
	private ArrayList<TrainInfoHoder> trainarray=new ArrayList<TrainInfoHoder>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(trainarray==null||trainarray.size()==0){
			return 1;
		}else{
			return trainarray.size();
		}
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textview;
		if(trainarray==null){
			textview=new TextView(parent.getContext());
			if(isupdated==true){
				textview.setText("无车次信息或网络有问题");
			}else{
				textview.setText("查询中。。。");
			}
			return textview;
		}else if(trainarray.size()==0){
			textview=new TextView(parent.getContext());
			if(isupdated==true){
				textview.setText("无当日车次信息");
			}else{
				textview.setText("查询中。。。");
			}
			return textview;
		}else{
			View view=inflater.inflate(R.layout.train_info_itemview, null);
			TrainInfoHoder hoder=trainarray.get(position);
			TextView traincode=(TextView) view.findViewById(R.id.train_code);
			traincode.setText(hoder.station_train_code);
			return view;
		}
		
	}

	public ArrayList<TrainInfoHoder> getTrainarray() {
		return trainarray;
	}

	public void setTrainarray(ArrayList<TrainInfoHoder> trainarray) {
		this.trainarray = trainarray; 
		isupdated=true;
	}

}
