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

import com.example.zero1.account.AccountManager;
import com.example.zero1.account.User;
import com.example.zero1.db.DBmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;


public  class TicketClient {

	final static String stationurl="https://kyfw.12306.cn/otn/resources/js/framework/station_name.js";
	final static String queryurlformat="https://kyfw.12306.cn/otn/leftTicket/queryT?leftTicketDTO.train_date=%s&leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=%s";
	final static String queryurl="https://kyfw.12306.cn/otn/leftTicket/queryT?";
	final static String loginurl="https://kyfw.12306.cn/otn/login/loginAysnSuggest";
	final static String passcodelogin="https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand";
	final static String logininiturl="https://kyfw.12306.cn/otn/login/init";
	final static String querynocomplete="https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete";
	final static String querymyorder="https://kyfw.12306.cn/otn/queryOrder/queryMyOrder";
	final static String ORDER_FLAG_TUIPIAO="my_refund";
	final static String ORDER_FLAG_ALL="my_order";
	final static String ORDER_FLAG_GAIQIAN="my_resign";
	final static String getpassengerurl="https://kyfw.12306.cn/otn/passengers/init";
	
	static List<Station> stations;
	AndroidHttpClient client=AndroidHttpClient.newInstance(null, null);
	BasicHttpParams param=new BasicHttpParams();
//	BasicHttpContext httpcontex=new BasicHttpContext();
	Context context;
	SSLContext sslcontex;
	DBmanager dbmanager;
	static AccountManager am=new AccountManager();
	
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
	
	public boolean loginRequst(User user,String random){
		String poststring="loginUserDTO.user_name="+user.getName()+"&userDTO.password="+user.getPwd()+"&randCode="+random;
		Log.i("f", poststring);
		HttpsURLConnection con;
		URL url;
		try {
			url=new URL(loginurl); 
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.setDoOutput(true);
			Log.i("fuck", user.getCookie());
			
			con.addRequestProperty("Cookie", user.getCookie());
			con.addRequestProperty("Cookie", "_jc_save_fromStation=%u8861%u9633%2CHYQ; _jc_save_toStation=%u6E58%u6F6D%2CXTQ; _jc_save_fromDate=2014-03-15; _jc_save_toDate=2014-03-15; _jc_save_wfdc_flag=dc");
			con.addRequestProperty("Connection", "keep-alive");
			con.addRequestProperty("Accept", "*/*");
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
			con.addRequestProperty("X-Requested-With", "XMLHttpRequest");
			con.addRequestProperty("Host", "kyfw.12306.cn");
			con.addRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			con.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
			con.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			con.addRequestProperty("Content-Length", "80");
			con.setFollowRedirects(true);
			//con.addRequestProperty(field, newValue);
			Map map=con.getRequestProperties();
			
//			con.setFixedLengthStreamingMode(poststring.length());
			OutputStream out = new BufferedOutputStream(con.getOutputStream());
			out.write(poststring.getBytes());
			out.flush();
//			String cookie=con.getHeaderField("Cookie");
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("fuck", new String(sb).toString());
			String s = new String(sb);
			String s1 =Utility.ParseLoginJson(s);
			if(s1.equals("Y"))
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
//	void loginInit(User user){
//		HttpsURLConnection con;
//		URL url;
//		try{
//			url=new URL(logininiturl); 
//			con=(HttpsURLConnection) url.openConnection();
//			con.setSSLSocketFactory(sslcontex.getSocketFactory());	
//			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
//			Map map=con.getHeaderFields();
//			List list=(List) map.get("Set-Cookie");
//			String s=list.toString();
//			String ss=(String) s.subSequence(s.indexOf("JSESSIONID="),s.indexOf(";"));
//			String ss2=(String) s.subSequence(s.indexOf("BIGipServerotn"),s.lastIndexOf(";"));
//			String cookie=ss+"; "+ss2;
//			user.setCookie(cookie);
//			Log.i("fuck", ss);
//		}catch (Exception e){
//			
//		}
//	}
	Bitmap getPassCode(User user,boolean isrefresh){
		Log.i("f", "entergetpasscode");
		HttpsURLConnection con;
		URL url;
		Bitmap bitmap;
		try { 
			if(isrefresh){
				double f=Math.random();
				String passcodeloginref=passcodelogin+String.valueOf(f);
				url=new URL(passcodeloginref); 
				con=(HttpsURLConnection) url.openConnection();
				con.setSSLSocketFactory(sslcontex.getSocketFactory());
				con.setRequestProperty("Cookie", user.getCookie());
				InputStream reader=con.getInputStream();
				bitmap = BitmapFactory.decodeStream(reader); 
				reader.close(); 
				return bitmap; 
			}else{
				url=new URL(passcodelogin); 
				con=(HttpsURLConnection) url.openConnection();
				con.setSSLSocketFactory(sslcontex.getSocketFactory());
				InputStream reader=con.getInputStream();
				Map map=con.getHeaderFields();
				List list=(List) map.get("Set-Cookie");
				user.setIdentity(list.toString());
				String s=list.toString();
				String ss=(String) s.subSequence(s.indexOf("JSESSIONID="),s.indexOf(";"));
				String ss2=(String) s.subSequence(s.indexOf("BIGipServerotn"),s.lastIndexOf(";"));
				String cookie=ss+";"+ss2;
				user.setCookie(cookie);
				Log.i("fuck", ss);
				bitmap = BitmapFactory.decodeStream(reader); 
				reader.close(); 
				return bitmap; 
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Order[] queryMyOrder(User user,String starttime,String endtime,String flag){
		String poststring = null;
		HttpsURLConnection con;
		URL url;
		if(flag.equals(ORDER_FLAG_ALL)){
			poststring="queryType=1&queryStartDate="+starttime+"&queryEndDate="+endtime+"&come_from_flag=my_order&pageSize=8&pageIndex=0&sequeue_train_name=";
		}else if(flag.equals(ORDER_FLAG_TUIPIAO)){
			poststring="queryType=1&queryStartDate="+starttime+"&queryEndDate="+endtime+"&come_from_flag=my_refund&pageSize=8&pageIndex=0&sequeue_train_name=";
		}else if(flag.equals(ORDER_FLAG_GAIQIAN)){
			poststring="queryType=1&queryStartDate="+starttime+"&queryEndDate="+endtime+"&come_from_flag=my_resign&pageSize=8&pageIndex=0&sequeue_train_name=";
		}
		try {
			url=new URL(poststring);
			con=(HttpsURLConnection)url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.setRequestProperty("Cookie", user.getCookie());
			
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("getorder", new String(sb).toString());
			String s = new String(sb);
			//to be continued
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Order> queryNocompleteOrder(User user){
		HttpsURLConnection con;
		URL url;
		String poststring="_json_att=";
		try {
			url=new URL(querynocomplete);
			con=(HttpsURLConnection) url.openConnection();
			con.setRequestProperty("Cookie", user.getCookie());
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.setDoOutput(true);
			OutputStream out = new BufferedOutputStream(con.getOutputStream());
			out.write(poststring.getBytes());
			out.flush();
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("getorder", new String(sb).toString());
			String s = new String(sb);
			ArrayList<Order> orders=Utility.parseNoCompleteOrderJson(s);
			return orders;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<Order> queryMyOrder(User user,String starttime,String endtime,String flag,String trainname){
		HttpsURLConnection con;
		URL url; 
		String poststring="queryType=1&queryStartDate=2014-07-26&queryEndDate=2014-08-26&come_from_flag=my_order&pageSize=8&pageIndex=0&sequeue_train_name=";
//		String poststring="queryType=1&queryStartDate="+starttime+"&queryEndDate="+endtime+"&come_from_flag="+flag+"&pageSize=8&pageIndex=0&sequeue_train_name="+trainname;
		try {
			url=new URL(querymyorder);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.addRequestProperty("cookie", user.getCookie());
			con.setDoOutput(true);
			OutputStream out = new BufferedOutputStream(con.getOutputStream());
			out.write(poststring.getBytes());
			out.flush();

			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("getorder", new String(sb).toString());
			String s = new String(sb);
			ArrayList<Order> orders=Utility.parseMyOrderJson(s);
			return orders;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Passenger> getPassengers(User user){
		HttpsURLConnection con;
		URL url;
		String poststring="_json_att=";
		try {
			url=new URL(getpassengerurl);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.addRequestProperty("Cookie", user.getCookie());
			con.setDoOutput(true);
			OutputStream out = new BufferedOutputStream(con.getOutputStream());
			out.write(poststring.getBytes());
			out.flush();
			
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("getpassengerstring", new String(sb).toString());
			String s = new String(sb);
			String ss=s.substring(s.indexOf("var passengers="), s.indexOf("}];"+2));
			Log.i("passengerstring", ss);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
