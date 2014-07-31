package com.example.zero1;

import java.util.ArrayList;

import com.example.zero1.account.User;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
	MyExListviewAdapter adapter=new MyExListviewAdapter(om);
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
		exlistview.setAdapter(adapter);
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
		ArrayList<Order> orders=new ArrayList<Order>();
		ArrayList<Order> nocompleteoders=new ArrayList<Order>();
		
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
				if(nocompleteoders!=null&&nocompleteoders.isEmpty()){
					new QueryNoCompleteOrderTask().execute("s");				
				}else if(!nocompleteoders.isEmpty()){
					OrderView order = new OrderView(parent.getContext());
					order.setOrderinfo(nocompleteoders.get(0));
				}
			case 1:
				
			}
			return new TextView(activity);
//			return new OrderView(parent.getContext());
//			return null;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			switch(groupPosition){
			case 0:
				if(nocompleteoders!=null&&nocompleteoders.isEmpty()){
					return 1;				
				}else if(nocompleteoders!=null&&!nocompleteoders.isEmpty()){
					return nocompleteoders.size();
				}
				break;
			case 1:
			default:
				return 1;
			}
//			return 2;
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
		private class QueryNoCompleteOrderTask extends AsyncTask<String, Void, ArrayList<Order>> {

			@Override
			protected ArrayList<Order> doInBackground(String... a) {
				// TODO Auto-generated method stub

//				String s=params[0].queryTrainInfo(TicketClient.queryurlformat,bundle.getString("date"),bundle.getString("from_station"),bundle.getString("to_station"), "ADULT");
				return om.getNoCompleteOrders();
				
			}
		     protected void onPostExecute(ArrayList<Order> result) {
		    	 Log.i("fuck", "notifydatasetchanged");
//		    	 listviewadapter.setTrainarray(result);
		    	 nocompleteoders=result;
		    	 adapter.notifyDataSetChanged();
		     }
		 
		 
		}
	} 

}
