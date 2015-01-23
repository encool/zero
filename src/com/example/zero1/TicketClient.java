package com.example.zero1;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	final static String preparequeryurlformat="https://kyfw.12306.cn/otn/leftTicket/log?leftTicketDTO.train_date=%s&leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=%s";
	final static String queryurlAgAndroidformat="https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=%s&queryDate=%s&from_station=%s&to_station=%s";
	
	final static String loginInitURL="https://kyfw.12306.cn/otn/login/init";
	
	final static String queryurl="https://kyfw.12306.cn/otn/leftTicket/query?";
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
//	AndroidHttpClient client=AndroidHttpClient.newInstance(null, null);
	BasicHttpParams param=new BasicHttpParams();
//	BasicHttpContext httpcontex=new BasicHttpContext();
	Context context;
	SSLContext sslcontex;
	DBmanager dbmanager;
//	static AccountManager am=new AccountManager();
	
	public TicketClient() {
		// TODO Auto-generated constructor stub
//		client=AndroidHttpClient.newInstance("a");
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
	public String queryTrainInfo(User user,String formatstring,String date,String from_station,String to_station,String type) {
		String queryUrl=String.format(formatstring, date,from_station,to_station,type);
		String prepareQueryUrl=String.format(preparequeryurlformat, date,from_station,to_station,type);
		Log.i("IntoQueryTrainInfoFuc", queryUrl);
		URL url;
		HttpsURLConnection con;
//		HttpGet request=new HttpGet(queryUrl);
//		client
		try {
//			String s = getMethodURL(new URL(prepareQueryUrl));
			url=new  URL(prepareQueryUrl);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.setRequestProperty("If-Modified-Since", "0");
			con.setRequestProperty("Cache-Control", "no-cache");
//			con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
//			con.setRequestProperty("Accept-Encoding", "gzip");
//			con.setRequestProperty("Connection", "keep-alive");
//			con.setRequestProperty("Host", "kyfw.12306.cn");
			if(user!=null){
				con.setRequestProperty("Cookie", user.getCookie());
				String str=readStream(con.getInputStream());
				Log.i("ticketclient","prepareQueryTrainInfoResult-->"+str);
				url=new URL(queryUrl);
				con=(HttpsURLConnection) url.openConnection();
				con.setSSLSocketFactory(sslcontex.getSocketFactory());
				con.setRequestProperty("Cookie", user.getCookie());
//				con.setRequestProperty("If-Modified-Since", "0");
//				con.setRequestProperty("Cache-Control", "no-cache");
				InputStreamReader reader=new InputStreamReader(con.getInputStream());
				char[] buffer = new char[1024];
				StringBuilder sb = new StringBuilder();
				while(reader.read(buffer)!=-1){
					sb.append(buffer);
				}
				Log.i("ticketclient",  "queryTTnforesult-->"+new String(sb).toString());
				return new String(sb);
			}else{
				Map map3=con.getRequestProperties();

				List list3=(List) map3.get("Set-Cookie");
				Iterator iterator3=map3.keySet().iterator();
				String s4;
				while(iterator3.hasNext()){
					s4=(String) iterator3.next();
					Log.i("logURL",s4+"-->"+map3.get(s4));
				}
				String str=readStream(con.getInputStream());
				Log.i("ticketclient","prepareQueryTrainInfoResult-->"+str);
				Map map=con.getHeaderFields();
				List list=(List) map.get("Set-Cookie");
				Iterator iterator=map.keySet().iterator();
				String s2;
				while(iterator.hasNext()){
					s2=(String) iterator.next();
					Log.i("logURL",s2+"-->"+map.get(s2));
				}
				

				
				
				String s=list.toString();
				String ss=(String) s.subSequence(s.indexOf("JSESSIONID="),s.indexOf(";"));
				String ss2=(String) s.subSequence(s.indexOf("BIGipServerotn"),s.lastIndexOf(";"));
				String cookie=ss+";"+ss2;
				Log.i("cookieStuff",cookie);				
				url=new URL(queryUrl);
//				con.disconnect();
//				Thread.sleep(1500);
				HttpsURLConnection cons=(HttpsURLConnection) url.openConnection();
				cons.setConnectTimeout(10000);
				System.setProperty("http.agent", "");
				cons.setSSLSocketFactory(sslcontex.getSocketFactory());
				cons.setRequestProperty("Cookie", cookie);
				cons.setRequestProperty("If-Modified-Since", "0");
				cons.setRequestProperty("Cache-Control", "no-cache");
//				cons.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//				cons.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
//				cons.setRequestProperty("Accept-Encoding", "gzip");
//				cons.setRequestProperty("Connection", "keep-alive");
//				cons.setRequestProperty("Host", "kyfw.12306.cn");
				InputStreamReader reader=new InputStreamReader(cons.getInputStream());
				
				Map map2 = cons.getHeaderFields();
				Iterator iterator2=map2.keySet().iterator();
				String s3;
				while(iterator2.hasNext()){
					s3=(String) iterator2.next();
					Log.i("queryURL",s3+"-->"+map2.get(s3));
				}
				
				BufferedReader bufferedreader=new BufferedReader(reader);
				char[] buffer = new char[1024];
				StringBuilder sb = new StringBuilder();
				while(bufferedreader.read(buffer)!=-1){
					sb.append(buffer);
				}
				Log.i("ticketclient",  "queryTTnforesult-->"+new String(sb).toString());
				return new String(sb);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String queryTrainInfoAgentAndroid(User user,String formatstring,String date,String from_station,String to_station,String type){
		String queryUrl=String.format(formatstring, type,date,from_station,to_station);
		URL url;
		HttpsURLConnection con;
		try {
			url = new URL(queryUrl);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.setRequestProperty("If-Modified-Since", "0");
			con.setRequestProperty("Cache-Control", "no-cache");
			String s=readStream(con.getInputStream());
			return s;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = new String(sb);
		Log.i("readStream", s);
		
		return s;		
	}
	public String getMethodURL(URL url){
		HttpsURLConnection con;
		Log.i("getURL", url.toString());
		try {
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			String s=readStream(con.getInputStream());
			con.disconnect();
			Map map = con.getHeaderFields();
			Iterator iterator=map.keySet().iterator();
			String ss;
			while(iterator.hasNext()){
				ss=(String) iterator.next();
				Log.i("getURL",ss+"-->"+map.get(ss));
			}
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
	
	public String getHideLoginValue(){
		Map map = new HashMap();
		String regex = "<input type=\"hidden\" name=\"\\w\" value=\"\\w\">";
		Pattern pattern=Pattern.compile(regex);
		try {
			URL url = new URL(logininiturl);
			HttpsURLConnection con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			String s=readStream(con.getInputStream());
			Matcher matcher=pattern.matcher(s);
			String str=matcher.group();
			Log.i("gethidevalue", str);
//			String key1=s.substring(s.indexOf(mapStr1)+mapStr1.length(),)
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regex;
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
			getHideLoginValue();
			con.addRequestProperty("Cookie", user.getCookie());
//			con.addRequestProperty("Cookie", "_jc_save_fromStation=%u8861%u9633%2CHYQ; _jc_save_toStation=%u6E58%u6F6D%2CXTQ; _jc_save_fromDate=2014-03-15; _jc_save_toDate=2014-03-15; _jc_save_wfdc_flag=dc");
//			con.addRequestProperty("Connection", "keep-alive");
//			con.addRequestProperty("Accept", "*/*");
//			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
//			con.addRequestProperty("X-Requested-With", "XMLHttpRequest");
//			con.addRequestProperty("Host", "kyfw.12306.cn");
//			con.addRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
//			con.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
//			con.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
		String poststring="queryType=1&queryStartDate=2014-08-26&queryEndDate=2014-10-8&come_from_flag=my_order&pageSize=8&pageIndex=0&sequeue_train_name=";
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
			String ss=s.substring(s.indexOf("var passengers=")+15, s.indexOf("}];")+2);
			Log.i("passengerstring", ss);
			return Utility.parsePassengerJson(ss);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean submitOrderRequest(TrainInfoHoder traininfo,User user){
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyymmdd");
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = dateformat.parse(traininfo.start_train_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String train_date=dateformat1.format(date);
		String urlstr="https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
		String postdata="secretStr="+traininfo.secretStr+
				"&train_date="+train_date+"&back_train_date="+QueryFragment.back_time+
						"&tour_flag=dc&purpose_codes="+QueryFragment.purpose_codes+"&query_from_station_name="+traininfo.from_station_name_ch+"" +
								"&query_to_station_name="+traininfo.to_station_name_ch+"&undefined";
		try {
			URL url=new URL(urlstr);
			HttpsURLConnection con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.addRequestProperty("Cookie", user.getCookie());
			con.setDoOutput(true);
			OutputStream out = new BufferedOutputStream(con.getOutputStream());
			out.write(postdata.getBytes());
			out.flush();
			
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			Log.i("submitorderrequest", new String(sb).toString());
			String s = new String(sb);
			//if jsonÖÐstatus Îªture ·µ»Øture
				return true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public  boolean checkUser(User user){
		final String checkuserurl="https://kyfw.12306.cn/otn/login/checkUser ";
		
		HttpsURLConnection con;
		try {
			URL url=new URL(checkuserurl);
			con = (HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.addRequestProperty("Cookie", user.getCookie());
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			String s = new String(sb);
			Log.i("getpassengerstring", s);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String confirmPassengerInitDc(User user){
		final String surl="https://kyfw.12306.cn/otn/confirmPassenger/initDc";
		HttpsURLConnection con;
		URL url;
		try {
			url = new URL(surl);
			con = (HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
			con.addRequestProperty("Cookie", user.getCookie());
			InputStreamReader reader=new InputStreamReader(con.getInputStream());
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(reader.read(buffer)!=-1){
				sb.append(buffer);
			}
			String s = new String(sb);
			String ss = s.substring(s.indexOf("'orderRequestDTO':"), s.indexOf("},'purpose_codes'")+1);//orderRequestDTO
			String sss = s.substring(s.indexOf("var ticketInfoForPassengerForm"), s.indexOf("var orderRequestDTO="));//ticketInfoForPassengerForm
			Log.i("ticketInfoForPassengerForm", sss);
			Log.i("orderRequestDTO", ss);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return surl;

	}
}
