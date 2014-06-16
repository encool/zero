package com.example.zero1.testcase;
import java.util.ArrayList;

import com.example.zero1.TicketClient;
import com.example.zero1.TrainInfoHoder;
import com.example.zero1.Utility;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class TicketClientTest extends InstrumentationTestCase {
	
    protected void setUp() {
    	
       
    }
	public void testQuery() {
//		fail("Not yet implemented");
//		query();
		
		TicketClient client=new TicketClient(getInstrumentation().getContext());
//		String s=client.queryTrainInfo(TicketClient.queryurlformat, "2014-05-01", "BJP", "GZQ", "ADULT");
//		Log.i("fuck", s);
//		ArrayList<TrainInfoHoder> array=Utility.ParseJsonToArray(s);
//		assertNotNull(array);
	}

}
