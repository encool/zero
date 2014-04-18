package com.example.zero1;

//import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
//import android.support.v7.*;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
//import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class AppActivity extends android.support.v7.app.ActionBarActivity {
	FragmentPagerAdapter ticketPageAdapter;
	ViewPager viewpager;
    private LayoutInflater inflater;
    TicketClient tc;
    com.example.zero1.db.DBmanager dbmanager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		viewpager=(ViewPager) findViewById(R.id.pager);
		viewpager.setAdapter(new TicketPagerAdater(getSupportFragmentManager()));
		tc=new TicketClient(getApplicationContext());
		new TicketClientInitTask().execute(tc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio_left_btn:
	            if (checked)
	            	 
	            break;
	        case R.id.radio_right_btn:
	            if (checked)
	                // Ninjas rule
	            break;
	    }
	}
	public void onStationSelectCilcked(View view){
		startActivity(new Intent(this,CityChoose.class));
	}
	public void onDateSelectClicked(View view){
		startActivity(new Intent(this,DateChoose.class));
	}
	public void onTimeIntervalClicked(View view){
//		startActivity(new Intent(this,DateChoose.class));
		Builder builder = new AlertDialog.Builder(this);
		inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.timeintervalselect, null);
        builder.setView(layout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
	}
	private class TicketClientInitTask extends AsyncTask<TicketClient, Void, String> {

		@Override
		protected String doInBackground(TicketClient... params) {
			// TODO Auto-generated method stub

			params[0].updateStationInfo();
			
			return null;
		}		
	}
}
