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

	LayoutInflater inflater;
	private ArrayList<TrainInfoHoder> trainarray=new ArrayList<TrainInfoHoder>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(trainarray==null){
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
			textview.setText("≤È—Ø÷–°£°£°£");
			return textview;
		}else{
			View view=inflater.inflate(R.layout.train_info_itemview, null);
			TrainInfoHoder hoder=trainarray.get(position);
			TextView traincode=(TextView) view.findViewById(R.id.train_code);
			traincode.setText(hoder.from_station_code);
			return view;
		}
		
	}

	public ArrayList<TrainInfoHoder> getTrainarray() {
		return trainarray;
	}

	public void setTrainarray(ArrayList<TrainInfoHoder> trainarray) {
		this.trainarray = trainarray; 
	}

}
