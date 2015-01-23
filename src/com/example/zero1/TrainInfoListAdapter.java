package com.example.zero1;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainInfoListAdapter extends BaseAdapter implements OnItemClickListener{
	
	public TrainInfoListAdapter(Context context,
			ArrayList<TrainInfoHoder> trainarray) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.trainarray = trainarray;
	}
	
	boolean isupdated=false; //�Ƿ��ѯ�ˣ������ѯ���Ľ��Ϊ�գ���ʾ��ѯʧ�ܣ����ˢ�°�ťʱ ��Ҫ�����������⡣
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
				textview.setText("�޳�����Ϣ������������");
			}else{
				textview.setText("��ѯ�С�����");
			}
			return textview;
		}else if(trainarray.size()==0){
			textview=new TextView(parent.getContext());
			if(isupdated==true){
				textview.setText("�޵��ճ�����Ϣ");
			}else{
				textview.setText("��ѯ�С�����");
			}
			return textview;
		}else{
			View view=inflater.inflate(R.layout.train_info_itemview, null);
			TrainInfoHoder hoder=trainarray.get(position);
			TextView traincode=(TextView) view.findViewById(R.id.train_code);
			traincode.setText(hoder.station_train_code);
			
			((TextView)view.findViewById(R.id.itemview_from_station)).setText(hoder.from_station_name_ch);
			((TextView)view.findViewById(R.id.itemview_to_station)).setText(hoder.to_station_name_ch);
			
			((TextView)view.findViewById(R.id.lishi)).setText(hoder.lishi);
			((TextView)view.findViewById(R.id.start_time)).setText(hoder.start_time);
			((TextView)view.findViewById(R.id.arrive_time)).setText(hoder.arrive_time);
			
			if(hoder.station_train_code.contains("G")){
				((TextView)view.findViewById(R.id.seattype_1)).setText("������:"+hoder.swz_num);
				((TextView)view.findViewById(R.id.seattype_2)).setText("һ����:"+hoder.zy_num);
				((TextView)view.findViewById(R.id.seattype_3)).setText("������:"+hoder.ze_num);
//				((LinearLayout)view).removeView(view.findViewById(R.id.seattype_4));
				((TextView)view.findViewById(R.id.seattype_4)).setVisibility(View.INVISIBLE);
			}else{
				((TextView)view.findViewById(R.id.seattype_1)).setText("����:"+hoder.rw_num);
				((TextView)view.findViewById(R.id.seattype_2)).setText("Ӳ��:"+hoder.yw_num);
				((TextView)view.findViewById(R.id.seattype_4)).setText("����:"+hoder.wz_num);
				((TextView)view.findViewById(R.id.seattype_3)).setText("Ӳ��:"+hoder.yz_num);
			}
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(!AppActivity.am.hasLoginedUser()){
			arg1.getContext().startActivity(new Intent(arg0.getContext(),LoginActivity.class));
		}else{
			Intent intent=new Intent(arg1.getContext(),BookViewActivity.class);
//			Bundle bundle=new Bundle();
//			bundle.putSerializable("train", trainarray.get(arg2));
			arg1.getContext().startActivity(intent.putExtra("train", trainarray.get(arg2)));
		}

	}

}
