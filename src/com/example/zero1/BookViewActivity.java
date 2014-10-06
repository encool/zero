package com.example.zero1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BookViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_view);
		traininfo=(TrainInfoHoder) this.getIntent().getSerializableExtra("train");
		intiView();
	}
	TrainInfoHoder traininfo=new TrainInfoHoder();
	
//	traininfo = bundle;
	private void intiView() {
		// TODO Auto-generated method stub
		TextView tv=(TextView) this.findViewById(R.id.bv_trainnum);
		tv.setText(traininfo.station_train_code);
		TextView bvdate=(TextView) this.findViewById(R.id.bv_date);
		bvdate.setText(traininfo.start_train_date_mmyueddri);
		TextView bvweek=(TextView) this.findViewById(R.id.bv_week);
		bvweek.setText(traininfo.start_train_date_week);
		TextView bvstarttime=(TextView) this.findViewById(R.id.bv_starttime);
		bvstarttime.setText(traininfo.start_time);
		TextView bvarrivetime=(TextView) this.findViewById(R.id.bv_arrivetime);
		bvarrivetime.setText(traininfo.arrive_time);
		TextView bvarrivestation=(TextView) this.findViewById(R.id.bv_arrivestation);
		bvarrivestation.setText(traininfo.to_station_name_ch);
		TextView bvfromstation=(TextView) this.findViewById(R.id.bv_startstation);
		bvfromstation.setText(traininfo.from_station_name_ch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
