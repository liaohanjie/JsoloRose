package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Start {

	public static void main(String[] args) throws IOException {
		MultiClient client = new MultiClient();
		client.start(4);
		
		BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
	     while(true){
	    	 try {
	    		 System.out.println("请输入:");
			     String next = strin.readLine();
			     client.nextChannel().writeAndFlush(next);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     }
	}
}
