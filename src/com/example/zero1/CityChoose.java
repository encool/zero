package com.example.zero1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

class CityChoose extends Activity {
	
	CityAdapter hcadpter;
	CityAdapter rcadpter;
	int[] a={1,2,3,4};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		hcadpter =new CityAdapter(a);
		GridView gdview=(GridView)findViewById(R.id.hotgd);
		gdview.setAdapter(hcadpter);
	}

}
