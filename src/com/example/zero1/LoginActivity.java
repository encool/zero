package com.example.zero1;

import com.example.zero1.account.User;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends Activity {
	EditText usernameview;
	EditText passwordview;
	ImageView imageview;
	User user=new User();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		usernameview=(EditText) findViewById(R.id.username);
		passwordview=(EditText) findViewById(R.id.password);
		imageview=(ImageView) findViewById(R.id.passcodeimage);
		setListener();
		new getLoginPassCodeTask().execute(user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	void setListener(){
		View button= this.findViewById(R.id.login);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username=usernameview.getText().toString();
				String password=passwordview.getText().toString();
				user.setName(username);
				user.setPwd(password);
				new LoginTask().execute(user);
			}});
	}
	private class LoginTask extends AsyncTask<User, Void,String > {

		@Override
		protected String doInBackground(User... params) {
			// TODO Auto-generated method stub
			Log.i("f", "enter login");
			AppActivity.tc.loginRequst(params[0], "asdd");
			
			return null;
		}		
	}
	private class getLoginPassCodeTask extends AsyncTask<User, Void,Bitmap > {

		@Override
		protected Bitmap doInBackground(User... params) {
			// TODO Auto-generated method stub
			Log.i("f", "enter login");
			Bitmap bitmap=AppActivity.tc.getPassCode(params[0]);
			
			return bitmap;
		}
	     protected void onPostExecute(Bitmap result) {
//	         showDialog("Downloaded " + result + " bytes");
	    	
	    	 imageview.setImageBitmap(result);
	     }

	}
	
}
