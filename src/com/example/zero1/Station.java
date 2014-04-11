package com.example.zero1;

public class Station{

	public String station_thr_code;
	public String station_name_ch;
	public String station_code;
	public String station_name_pingyin;
	public String station_pingyin_shou;
	public Station() {
		// TODO Auto-generated constructor stub
	}
	public Station(String station_thr_code, String station_name_ch,
			String station_code, String station_name_pingyin,
			String station_pingyin_shou) {
		super();
		this.station_thr_code = station_thr_code;
		this.station_name_ch = station_name_ch;
		this.station_code = station_code;
		this.station_name_pingyin = station_name_pingyin;
		this.station_pingyin_shou = station_pingyin_shou;
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
	public String getStation_thr_code() {
		return station_thr_code;
	}
	public void setStation_thr_code(String station_thr_code) {
		this.station_thr_code = station_thr_code;
	}
	public String getStation_pingyin_shou() {
		return station_pingyin_shou;
	}
	public void setStation_pingyin_shou(String station_pingyin_shou) {
		this.station_pingyin_shou = station_pingyin_shou;
	}
}