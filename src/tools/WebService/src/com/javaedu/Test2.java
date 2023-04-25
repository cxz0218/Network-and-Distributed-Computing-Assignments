package com.javaedu;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;

public class Test2 {

	/**
	 * URL Connection方式
	 * 
	 */
	public static void main(String[] args) throws Exception {
		//服务的地址
        URL wsUrl = new URL("http://127.0.0.1:8001/webservice/hello");
        
        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
        
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        
        OutputStream os = conn.getOutputStream();
        
        //请求体
        String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns=\"http://www.javaedu.com\">" + 
                      "<soap:Body><tns:sayHi><arg0>Mary</arg0><arg1>2</arg1></tns:sayHi></soap:Body></soap:Envelope>";
        
        os.write(soap.getBytes());
        
        InputStream is = conn.getInputStream();
        
        byte[] b = new byte[1024];
        int len = 0;
        String s = "";
        while((len = is.read(b)) != -1){
            String ss = new String(b,0,len,"UTF-8");
            s += ss;
        }
        System.out.println(s);
        
        is.close();
        os.close();
        conn.disconnect();

	}

}
