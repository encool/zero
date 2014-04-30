package com.example.zero1;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class TrainsInfoActivity extends Activity {
	
	ActionBar actionbar;
	Bundle bundle;
	TrainInfoListAdapter listviewadapter;
	ArrayList<TrainInfoHoder> arraylist=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trains_info);
		actionbar=getActionBar();
		//设置adapter
		ListView listview=(ListView) findViewById(R.id.trainlist);
		listviewadapter=new TrainInfoListAdapter(this,arraylist); 
		listview.setAdapter(listviewadapter);
		//初始化bundle asynchronous要用
		bundle=getIntent().getBundleExtra("traininfo");
		new TicketClientQueryTrainTask().execute(AppActivity.tc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trains_info, menu);
		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	private class TicketClientQueryTrainTask extends AsyncTask<TicketClient, Void, ArrayList<TrainInfoHoder>> {

		@Override
		protected ArrayList<TrainInfoHoder> doInBackground(TicketClient... params) {
			// TODO Auto-generated method stub

			String s=params[0].queryTrainInfo(AppActivity.tc.queryurlformat,bundle.getString("date"),bundle.getString("from_station"),bundle.getString("to_station"), "ADULT");
			return Utility.ParseJsonToArray(s);
			
		}
	     protected void onPostExecute(ArrayList<TrainInfoHoder> result) {
	    	 Log.i("fuck", "notifydatasetchanged");
	    	 listviewadapter.setTrainarray(result);
	         listviewadapter.notifyDataSetChanged();
	     }
	 
	 
	}
}
