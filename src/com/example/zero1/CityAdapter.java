package com.example.zero1;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressWarnings("unused")
public class CityAdapter extends BaseAdapter {

    Resources resources;
	private int[] cityids;
	
	public CityAdapter(int[] a) {
		super();
		this.cityids = a;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cityids.length;
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
		
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//		LayoutParams gravity=new LayoutParams(position, position);
		param.gravity=0x11;
		TextView itemview=new TextView(parent.getContext());
		itemview.setLayoutParams(param);
		itemview.setBackgroundResource(R.drawable.normal_bg_selector);
		itemview.setText(Integer.toString(position));
		return itemview;
	}

}
