package com.example.zero1;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.DefaultedHttpContext;
import org.apache.http.protocol.HttpContext;

import com.example.zero1.account.User;
import com.example.zero1.db.DBmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;


public  class TicketClient {

	final static String stationurl="https://kyfw.12306.cn/otn/resources/js/framework/station_name.js";
	final static String queryurlformat="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=%s&leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=%s";
	final static String queryurl="https://kyfw.12306.cn/otn/leftTicket/query?";
	final static String loginurl="https://kyfw.12306.cn/otn/login/loginAysnSuggest";
	final static String passcodelogin="https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand";

	static List<Station> stations;
	AndroidHttpClient client=AndroidHttpClient.newInstance(null, null);
	BasicHttpParams param=new BasicHttpParams();
//	BasicHttpContext httpcontex=new BasicHttpContext();
	Context context;
	SSLContext sslcontex;
	DBmanager dbmanager;
	public TicketClient() {
		// TODO Auto-generated constructor stub
		client=AndroidHttpClient.newInstance("a");
//		httpcontextInit();
//		client.
	}
	public TicketClient(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
//		client=AndroidHttpClient.newInstance(null, context);
		MySslContextFactory sslfactory=new MySslContextFactory(context,"srca.cer");	
//		sslfactory.init();
		sslcontex=sslfactory.getSslcontext();
//		httpcontextInit();
//		client.
	}
//	void httpcontextInit(){
//		httpcontex.setAttribute("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		httpcontex.setAttribute("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
//		httpcontex.setAttribute("Accept-Encoding", "gzip");
//		httpcontex.setAttribute("Connection", "keep-alive");
//		httpcontex.setAttribute("Host", "kyfw.12306.cn");
//	}
	public String queryTrainInfo(String formatstring,String date,String from_station,String to_station,String type) {
		String queryUrl=String.format(formatstring, date,from_station,to_station,type);
		Log.i("fuck", queryUrl);
		URL url;
		HttpsURLConnection con;
//		HttpGet request=new HttpGet(queryUrl);
//		client
		try {
			url=new URL(queryUrl);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
//			BufferedInputStream in=new BufferedInputStream(con.getInputStream());
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("fuck", new String(sb).toString());
			return new String(sb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String queryStationURL(){
		URL url;
		HttpsURLConnection con;
		Log.i("fuck", "enter fuc queryStarion");
		try {
			url=new URL(stationurl);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			String s=readStream(con.getInputStream());
			con.disconnect();
			return s;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String readStream(InputStream in){
//		BufferedInputStream buin=new BufferedInputStream(in);
		InputStream in1 = new BufferedInputStream(in);
		InputStreamReader reader=new InputStreamReader(in1);
		char[] buffer = new char[1024];
		StringBuilder sb = new StringBuilder();
		try {
			int n=reader.read(buffer);
			while(n!=-1){
				String s=new String(buffer,0,n);
				sb.append(s);
				n=reader.read(buffer);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("fuck", new String(sb));
		
		return new String(sb);		
	}
	public boolean updateStationInfo(){
		dbmanager=new DBmanager(context);
		if(dbmanager.isNeedUpdateStation()){
			dbmanager.deleteData("station");
			String s=this.queryStationURL();
			if(s!=null){
				TicketClient.stations=Utility.splitStringToArray(s, "@", "\\|");
				dbmanager.add(TicketClient.stations);
				dbmanager.updateStationTableInfo(TicketClient.stations.size());
				return true;
			}
		}
		dbmanager.closeBD();
		return false;
	}
	
	public String loginRequst(User user,String random){
		String poststring="loginUserDTO.user_name="+user.getName()+"&userDTO.password="+user.getPwd()+"&randCode="+random;
		Log.i("f", poststring);
		HttpsURLConnection con;
		URL url;
		try {
			url=new URL(loginurl); 
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.setDoOutput(true);
//			con.setFixedLengthStreamingMode(poststring.length());
			OutputStream out = new BufferedOutputStream(con.getOutputStream());
			out.write(poststring.getBytes());
//			String cookie=con.getHeaderField("Cookie");
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("fuck", new String(sb).toString());
			return new String(sb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	Bitmap getPassCode(User user){
		Log.i("f", "entergetpasscode");
		HttpsURLConnection con;
		URL url;
		Bitmap bitmap;
		try { 
			url=new URL(passcodelogin); 
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			InputStream reader=con.getInputStream();
			Map map=con.getHeaderFields();
			List list=(List) map.get("Set-Cookie");
			user.setIdentity(list.toString());
			bitmap = BitmapFactory.decodeStream(reader); 
			reader.close(); 
			return bitmap; 
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
