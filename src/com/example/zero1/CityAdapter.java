package com.example.zero1;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CityAdapter extends BaseAdapter {

    Resources resources;
	private ArrayList<Station> stations;
	private LayoutInflater mInflater;
	
	public CityAdapter(ArrayList<Station> hotstation, Context context) {
		super();
		this.stations = hotstation;
		mInflater = LayoutInflater.from(context);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stations.size();
	}

	@Override
	public Object getItem(int arg0) {
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
//		 XmlPullParser parser = resources.getXml(R.layout.cityitem_layout);
//		 AttributeSet attributes = Xml.asAttributeSet(parser);
//		 View itemview =new View(parent.getContext(),attributes);
//		 itemview.findViewById(R.id.cityview);
		
//		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//		LayoutParams gravity=new LayoutParams(position, position);
//		param.gravity=Gravity.CENTER_HORIZONTAL;
//		LinearLayout rootview=new LinearLayout(parent.getContext());		
//		TextView itemview=new TextView(parent.getContext());
//		rootview.addView(itemview,param);
//		rootview.setBackgroundResource(R.drawable.normal_bgcolor_selector);
//		rootview.setMinimumHeight(64);
//		itemview.setPadding(64, 0, 0, 0);
//		itemview.setGravity(Gravity.LEFT|Gravity.CENTER);
//		itemview.setTextSize(16);
//		itemview.setText(Integer.toString(position)+"…œ∫£");
		if(convertView == null){
//			mInflater=(LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.cityitem_layout, null);
			TextView itemview=(TextView) convertView.findViewById(R.id.cityview);
			itemview.setText(Integer.toString(position)+stations.get(position).getStation_name_ch());
		}
		
		
		return convertView;
	}

}
