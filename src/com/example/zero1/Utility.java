package com.example.zero1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Utility {
	public static List<Station> splitStringToArray(String source,String mark1,String mark2){
		ArrayList<Station> stations = new ArrayList<Station>();
		if(source!=null){
			String[] s=source.split(mark1);
			for(int i=1;i<s.length;i++){
				String s1[]=s[i].split(mark2);
				Station station=new Station(s1[0],s1[1],s1[2],s1[3],s1[4]);
				stations.add(station);
			}
			return stations;
		}
		return null;	
	}
	public static String prepareString(String s){
		StringBuilder sb=new StringBuilder();
		boolean lastiseng = false;
//		int i=0;
//		char c=s.charAt(i);
//		Character.UnicodeBlock cub = Character.UnicodeBlock.of(c); 
//		if(cub != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS){
//			for(i=1;i<s.length();i++){
//				c=s.charAt(i);
//				if(){
//					
//				}
//				sb.append(c);
//			}
//			sb.append(c);
//		}
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			Character.UnicodeBlock cub = Character.UnicodeBlock.of(c); 
			if(cub==Character.UnicodeBlock.BASIC_LATIN){
				if(i==0||lastiseng==true){
					sb.append(c);
					lastiseng=true;
				}else{
					break;
				}
			}else if(cub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
			{
				if(i==0||lastiseng==false){
					sb.append(c);
					lastiseng=false;
				}else{
					break;
				}	
			}
		}
			
		

		return new String(sb);		
	}
	public static ArrayList<TrainInfoHoder> ParseJsonToArray(String s){
		JSONTokener jsonparser=new JSONTokener(s);
		ArrayList<TrainInfoHoder> array=new ArrayList<TrainInfoHoder>();
		try { 
			TrainInfoHoder infohoder;
			JSONObject traininfo=(JSONObject) jsonparser.nextValue();
//			String validateMessagesShowId=traininfo.getString("validateMessagesShowId");
			String status=traininfo.getString("status");
			JSONArray data=traininfo.getJSONArray("data");				
			if(status.equals(new String("true"))){
				for(int i=0;i<data.length();i++){  
					
					JSONObject object=data.getJSONObject(i);
					
					JSONObject jsonob=object.getJSONObject("queryLeftNewDTO");
					if(jsonob!=null){
						infohoder=new TrainInfoHoder();
						infohoder.secretStr=object.getString("secretStr"); 
						infohoder.arrive_time=jsonob.getString("arrive_time");
						infohoder.end_starion_code=jsonob.getString("end_station_telecode");
						infohoder.end_station_name_ch=jsonob.getString("end_station_name");
						infohoder.from_station_code=jsonob.getString("from_station_telecode");
						infohoder.from_station_name_ch=jsonob.getString("from_station_name");
						infohoder.lishi=jsonob.getString("lishi");
						infohoder.rw_num=jsonob.getString("rw_num");
						infohoder.yw_num=jsonob.getString("yw_num");
						infohoder.yz_num=jsonob.getString("yz_num");
						infohoder.wz_num=jsonob.getString("wz_num");
						infohoder.zy_num=jsonob.getString("zy_num");
						infohoder.ze_num=jsonob.getString("ze_num");
						infohoder.swz_num=jsonob.getString("wz_num");
						infohoder.start_train_date=jsonob.getString("start_train_date");
						infohoder.start_station_code=jsonob.getString("start_station_telecode");
						infohoder.start_station_name_ch=jsonob.getString("start_station_name");
						infohoder.start_time=jsonob.getString("start_time");
						infohoder.station_train_code=jsonob.getString("station_train_code");
						infohoder.start_station_name_ch=jsonob.getString("start_station_name");
						infohoder.to_station_code=jsonob.getString("to_station_telecode");
						infohoder.to_station_name_ch=jsonob.getString("to_station_name");
						infohoder.convertDateFormat();
						array.add(infohoder);
					}

					
				}
				
			}			
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return array;
	}
	public static String ParseLoginJson(String s){
		JSONTokener jsonparser=new JSONTokener(s);
		try {
			JSONObject logininfo=(JSONObject) jsonparser.nextValue();
			JSONObject jsonob=logininfo.getJSONObject("data");
			String s1 =jsonob.getString("loginCheck");
			return s1;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
	public static ArrayList<Order> parseNoCompleteOrderJson(String s){
		JSONTokener jsonparser=new JSONTokener(s);
		JSONObject logininfo;
		ArrayList<Order> orders=new ArrayList<Order>();
		try {
			logininfo = (JSONObject) jsonparser.nextValue();
			JSONObject jsonob=logininfo.getJSONObject("data");
//			JSONObject jsonob1=jsonob.getJSONObject("orderDBList");
			JSONArray jsonarray=jsonob.getJSONArray("orderDBList");
			for(int i=0;i<jsonarray.length();i++){
				JSONObject orderjson=jsonarray.getJSONObject(i);
				Order order=new Order();
				order.ordernum=orderjson.getString("sequence_no");
				order.start_date=orderjson.getString("start_train_date_page");
				order.cancel_flag=orderjson.getString("cancel_flag");
				order.resign_flag=orderjson.getString("resign_flag");
				order.return_flag=orderjson.getString("return_flag");
//				order.status=orderjson.getString("ticket_status_name");
				order.start_time=orderjson.getString("start_time_page");

				JSONArray tickets=orderjson.getJSONArray("tickets");
				for(int i1=0;i1<tickets.length();i1++){
					JSONObject ticket=tickets.getJSONObject(i);
					
					JSONObject stationTrainDTO=ticket.getJSONObject("stationTrainDTO");
					order.startstation=stationTrainDTO.getString("from_station_name");
					order.arrivestation=stationTrainDTO.getString("to_station_name");
					
					JSONObject passenger=ticket.getJSONObject("passengerDTO");
					order.passengername=passenger.getString("passenger_name");
					
					order.status=ticket.getString("ticket_status_name");
					order.coach_no=ticket.getString("coach_no");
					order.seat_name=ticket.getString("seat_name");
					order.seat_type_name=ticket.getString("seat_type_name");
					
				}

				
				orders.add(order);
			}
			return orders;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return orders;
	}
	
	public static ArrayList<Order> parseMyOrderJson(String s){
		JSONTokener jsonparser=new JSONTokener(s);
		JSONObject logininfo;
		ArrayList<Order> orders=new ArrayList<Order>();
		try {
			logininfo = (JSONObject) jsonparser.nextValue();
			JSONObject jsonob=logininfo.getJSONObject("data");
//			JSONObject jsonob1=jsonob.getJSONObject("orderDBList");
			JSONArray jsonarray=jsonob.getJSONArray("OrderDTODataList");
			for(int i=0;i<jsonarray.length();i++){
				JSONObject orderjson=jsonarray.getJSONObject(i);
				Order order=new Order();
				order.ordernum=orderjson.getString("sequence_no");
				order.start_date=orderjson.getString("start_train_date_page");
				order.cancel_flag=orderjson.getString("cancel_flag");
				order.resign_flag=orderjson.getString("resign_flag");
				order.return_flag=orderjson.getString("return_flag");
//				order.status=orderjson.getString("ticket_status_name");
				order.start_time=orderjson.getString("start_time_page");

				JSONArray tickets=orderjson.getJSONArray("tickets");
				for(int i1=0;i1<tickets.length();i1++){
					JSONObject ticket=tickets.getJSONObject(i);
					
					JSONObject stationTrainDTO=ticket.getJSONObject("stationTrainDTO");
					order.startstation=stationTrainDTO.getString("from_station_name");
					order.arrivestation=stationTrainDTO.getString("to_station_name");
					
					JSONObject passenger=ticket.getJSONObject("passengerDTO");
					order.passengername=passenger.getString("passenger_name");
					
					order.status=ticket.getString("ticket_status_name");
					order.coach_no=ticket.getString("coach_no");
					order.seat_name=ticket.getString("seat_name");
					order.seat_type_name=ticket.getString("seat_type_name");
					
				}

				
				orders.add(order);
			}
			return orders;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return orders;
	}
	public static ArrayList<Passenger> parsePassengerJson(String s){
		String ss="{\"passengers\":"+s+"}";
		ArrayList<Passenger> passengers=new ArrayList<Passenger>();
		JSONTokener jsontoken=new JSONTokener(ss);
		try {
			JSONObject jsonob=(JSONObject) jsontoken.nextValue();
			JSONArray jsonarray=jsonob.getJSONArray("passengers");
			for(int i=0;i<jsonarray.length();i++){
				JSONObject jb=jsonarray.getJSONObject(i);
				Passenger pa=new Passenger();
				pa.id=jb.getString("passenger_id_no");
				String name=jb.getString("passenger_name");
				pa.name=URLDecoder.decode(name, "UTF-8");//url±àÂë
				pa.phonenum=jb.getString("mobile_no");
				String type=jb.getString("passenger_type_name");
				pa.type=URLDecoder.decode(type, "UTF-8");//url bianma
				passengers.add(pa);
			}
			int i=1;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passengers;
	}
}
