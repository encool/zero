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
				adapter.new QueryNoCompleteOrderTask().execute("s");
				adapter.status=1;
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
		int status=0;//0未查询 1查询中 2查询完毕
		
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
				if(status==2&&nocompleteoders==null){
					TextView tv=new TextView(activity);
					tv.setText("查询出错");
					return tv;
				}
				else if(status==2&&nocompleteoders.isEmpty()){
					TextView tv=new TextView(activity);
					tv.setText("没有相关信息");
					return tv;
				}else if(status==2&&!nocompleteoders.isEmpty()){
					OrderView order = new OrderView(parent.getContext());
					order.setOrderinfo(nocompleteoders.get(0));
					return order;
				}else if(status==1){
					TextView tv=new TextView(activity);
					tv.setText("查询中");
					return tv;
				}
				break;
			case 1:
				
			}
			TextView tv=new TextView(activity);
			tv.setText("查询中");
			return tv;

//			return new OrderView(parent.getContext());
//			return null;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			switch(groupPosition){
			case 0:
				if(status==1){
					return 1;				
				}else if(status==2&&nocompleteoders!=null&&!nocompleteoders.isEmpty()){
					return nocompleteoders.size();
				}else if(status==2&&nocompleteoders!=null&&nocompleteoders.isEmpty()){
					return 1;
				}
				break;
			case 1:
			default:
				return 1;
				
			}
//			return 2;
			return 1;
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
		    	 status=2;
		    	 adapter.notifyDataSetChanged();
		     }
		 
		 
		}
	} 

}
