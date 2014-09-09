package com.example.zero1;
import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class InfoFragment extends Fragment {

	
	Activity activity;
	ExpandableListView expandablelistview;
	InfoExListviewAdapter adapter;

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity=getActivity();
		setView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_info, container,false);
		return view;
	}
	public void setView(){
		expandablelistview=(ExpandableListView) activity.findViewById(R.id.info_list); 
		adapter=new InfoExListviewAdapter();
		expandablelistview.setAdapter(adapter);
		expandablelistview.setOnGroupClickListener(new OnGroupClickListener(){

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(!AppActivity.am.hasLoginedUser()){
					startActivity(new Intent(parent.getContext(),LoginActivity.class));
					return true;
				}
				switch(groupPosition){
				case 0:

				case 1:
					if(adapter.querypassengerstatus==0){
						adapter.new QueryPassengerTask().execute("s"); 
						adapter.querypassengerstatus=1;
					}
					break;
				}
				return false;
			}
			
		});
	}
	private class InfoExListviewAdapter extends BaseExpandableListAdapter{
		
		int querypassengerstatus=0;//0 doesnt do,1 doing, 2 complete.
		ArrayList<Passenger> passengers=new ArrayList<Passenger>();
		
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
			switch(groupPosition){
			case 0:
			case 1:
				if(querypassengerstatus==0&&passengers==null){
					TextView tv=new TextView(activity);
					tv.setText("没有相关信息");
					return tv;
				}else if(querypassengerstatus==1){
					TextView tv=new TextView(activity);
					tv.setText("查询中");
					return tv;
				}else if(querypassengerstatus==2&&passengers!=null){
					if(childPosition==0){
						View v=LayoutInflater.from(activity).inflate(R.layout.passengerview_title, null);
						return v;
					}else{
						PassengerView pv=new PassengerView(activity,null);
						pv.initView();
						pv.setPassengerInfo(passengers.get(childPosition-1));
						return pv;
					}

				}
			}
			return null;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			switch(groupPosition){
			case 0:
				break;
			case 1:
				return passengers.size()+1;
			}
			return 0;
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

			// TODO Auto-generated method stub
			switch(groupPosition){
			case 0:
				if(convertView==null){
					TextView tv=new TextView(parent.getContext());
					tv.setText("      个人信息");
					tv.setTextSize(18);
					return tv;
				}
				return convertView;
//				TextView tv=new TextView(parent.getContext());
//				tv.setText("    未完成订单");
//				tv.setTextSize(20);
//				return tv;
			case 1:
				TextView tv2=new TextView(parent.getContext());
				tv2.setText("      常用联系人");
				tv2.setTextSize(18);
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
		private class QueryPassengerTask extends AsyncTask<String, Void, ArrayList<Passenger>> {

			@Override
			protected ArrayList<Passenger> doInBackground(String... a) {
				// TODO Auto-generated method stub

//				String s=params[0].queryTrainInfo(TicketClient.queryurlformat,bundle.getString("date"),bundle.getString("from_station"),bundle.getString("to_station"), "ADULT");
				return AppActivity.tc.getPassengers(AppActivity.am.getLoginedUser(null));
				
			}
		     protected void onPostExecute(ArrayList<Passenger> result) {
		    	 Log.i("fuck", "notifydatasetchanged");
//		    	 listviewadapter.setTrainarray(result);
		    	 passengers=result;
		    	 querypassengerstatus=2;
		    	 adapter.notifyDataSetChanged();
		     }
		 
		 
		}
		
	}

}
