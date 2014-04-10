package com.example.zero1;

public class Utility {
	public static TicketClient.Station[] splitStringToArray(String source,String mark1,String mark2){
		TicketClient.Station[] stations = new TicketClient.Station[3072];
		String[] s=source.split(mark1);
		for(int i=0;i<s.length;i++){
			String s1[]=s[i].split(mark2);
			TicketClient.Station station=new TicketClient.Station(s1[1],s1[2],s1[3]);
			stations[i]=station;
		}
		return null;	
	}
}
