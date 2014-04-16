package com.example.zero1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;
import android.content.res.AssetManager;


public class MySslContextFactory {
	String crtfilepath;
	SSLContext sslcontext;
	Context context;
	public MySslContextFactory(Context context, String crtfilepath) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.crtfilepath=crtfilepath;
		init();
	}
	public boolean loadAndCreatContext(){
		sslcontext=CreateMySslcontext(LoadMyCa(crtfilepath));
		if(sslcontext==null)
			return false;
		return true;
	}
	//@parm f *.crt
	public boolean init(){
		if(sslcontext==null){
			if(loadAndCreatContext())
				return true;			
		}
		return false;
	}
	private java.security.cert.Certificate LoadMyCa(String f){
		InputStream caInput = null;
		java.security.cert.Certificate ca = null; 
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			AssetManager am = context.getAssets();  
			InputStream is = am.open("srca.cer"); 
			caInput = new BufferedInputStream(is);
			ca=cf.generateCertificate(caInput);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		} 
		return ca;
	}
	private SSLContext CreateMySslcontext(java.security.cert.Certificate ca){
		SSLContext context = null;
		try {
			// Create a KeyStore containing our trusted CAs  
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			// Create a TrustManager that trusts the CAs in our KeyStore 
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm(); 
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm); 
			tmf.init(keyStore); 
			context = SSLContext.getInstance("TLS"); 
			context.init(null, tmf.getTrustManagers(), null); 
		} catch (Exception e) {
			// TODO: handle exception
			
		}

		return context;
		
	}
	public SSLContext getSslcontext() {
		if(sslcontext!=null)
			return sslcontext;
		return null;
	}



}
