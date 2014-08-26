package com.example.zero1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

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
		
		//����adapter
		ListView listview=(ListView) findViewById(R.id.trainlist);
		listviewadapter=new TrainInfoListAdapter(this,arraylist); 
		listview.setAdapter(listviewadapter);
		//��ʼ��bundle asynchronousҪ��
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

			String s=params[0].queryTrainInfo(TicketClient.queryurlformat,bundle.getString("date"),bundle.getString("from_station"),bundle.getString("to_station"), "ADULT");
			return Utility.ParseJsonToArray(s);
			
		}
	     protected void onPostExecute(ArrayList<TrainInfoHoder> result) {
	    	 Log.i("fuck", "notifydatasetchanged");
	    	 listviewadapter.setTrainarray(result);
	         listviewadapter.notifyDataSetChanged();
	     }
	 
	 
	}
	private PopupWindow mPopupWindow;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:	//���¼���
				View view = getLayoutInflater().inflate(R.layout.activity_trains_info_control, null);
				//mPopupWindow����ʾʱ
				if (mPopupWindow == null) {
					//����mPopupWindow
					mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					//ע��buttonҪ�Ӱ󶨵Ĳ�������
					Button button = (Button) view.findViewById(R.id.daybefor);
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
						}});
				}

//					//mPopupWindow��ʾʱ��Ϊ��ʧ
//					if (mPopupWindow.isShowing()) {
//						mPopupWindow.dismiss();
//					}

					//����mPopupWindowΪ��ʾ��������λ��
				mPopupWindow.showAtLocation(findViewById(R.id.trainlist),
							Gravity.RIGHT | Gravity.BOTTOM, 0, 0);		
				break;
			case MotionEvent.ACTION_UP: //̧�����
				break;

			default:
				break;
		}
		//ע�⣺����Ҫ����true
		return true;
//		return super.onTouchEvent(event);
	}
}
