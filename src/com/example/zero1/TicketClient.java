package com.example.zero1;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.DefaultedHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;


public  class TicketClient {

	final static String stationurl="https://kyfw.12306.cn/otn/resources/js/framework/station_name.js";
	final static String queryurlformat="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=%s&leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=%s";
	final static String queryurl="https://kyfw.12306.cn/otn/leftTicket/query?";
	static Station[] stations=new Station[3072];
	AndroidHttpClient client=AndroidHttpClient.newInstance(null, null);
	BasicHttpParams param=new BasicHttpParams();
//	BasicHttpContext httpcontex=new BasicHttpContext();
	Context context;
	SSLContext sslcontex;
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
	public String queryStation(){
		URL url;
		HttpsURLConnection con;
		Log.i("fuck", "enter fuc queryStarion");
		try {
			url=new URL(stationurl);
			con=(HttpsURLConnection) url.openConnection();
			con.setSSLSocketFactory(sslcontex.getSocketFactory());
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("fuck", new String(sb));
		return new String(sb);		
	}
	public static class Station{
		String station_name_ch;
		String station_code;
		String station_name_pingyin;
		public Station(String station_name_ch, String station_code,
				String station_name_pingyin) {
			this.station_name_ch = station_name_ch;
			this.station_code = station_code;
			this.station_name_pingyin = station_name_pingyin;
		}
		public Station() {
			// TODO Auto-generated constructor stub
		}
		public String getStation_name_ch() {
			return station_name_ch;
		}
		public void setStation_name_ch(String station_name_ch) {
			this.station_name_ch = station_name_ch;
		}
		public String getStation_code() {
			return station_code;
		}
		public void setStation_code(String station_code) {
			this.station_code = station_code;
		}
		public String getStation_name_pingyin() {
			return station_name_pingyin;
		}
		public void setStation_name_pingyin(String station_name_pingyin) {
			this.station_name_pingyin = station_name_pingyin;
		}
	}
}