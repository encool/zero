package com.example.zero1;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.conn.scheme.HostNameResolver;

public class MyHostVerifier implements HostnameVerifier {

	public MyHostVerifier() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean verify(String hostname, SSLSession session) {
		// TODO Auto-generated method stub
		return true;
	}



}
