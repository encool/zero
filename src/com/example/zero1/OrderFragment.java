package com.example.zero1;

import com.example.zero1.account.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class OrderFragment extends Fragment {

	Activity activity;
	ExpandableListView exlistview;
	static OrderManager om=new OrderManager(AppActivity.tc,AppActivity.am.getLoginedUser(null));
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootview=inflater.inflate(R.layout.fragment_profile, container,false);	
		return rootview;
	}
	
	private void setView(){
		exlistview=(ExpandableListView) activity.findViewById(R.id.expandablelistview);
		exlistview.setAdapter(new MyExListviewAdapter(om));
//		exlistview.setGroupIndicator(null);
		exlistview.setOnGroupClickListener(new OnGroupClickListener(){

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(!om.isUserLogined()){
					startActivity(new Intent(parent.getContext(),LoginActivity.class));
					return true;
				}
				return false;
			}
			
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity=getActivity();
		setView();
	}
	private class MyExListviewAdapter extends BaseExpandableListAdapter{
		
		public MyExListviewAdapter(OrderManager om){
			super();
			this.om=om;
		}
		OrderManager om;

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return new OrderView(parent.getContext());
//			return null;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			switch(groupPosition){
			case 0:
				if(convertView!=null){
					TextView tv=new TextView(parent.getContext());
					tv.setText("    未完成订单");
					tv.setTextSize(20);
					return tv;
				}
//				TextView tv=new TextView(parent.getContext());
//				tv.setText("    未完成订单");
//				tv.setTextSize(20);
//				return tv;
			case 1:
				TextView tv2=new TextView(parent.getContext());
				tv2.setText("    已完成订单");
				tv2.setTextSize(20);
				return tv2;
			}
			return null;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
		
	} 

}
