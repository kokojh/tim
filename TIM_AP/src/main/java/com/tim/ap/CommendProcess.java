package com.tim.ap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommendProcess {
	
	public void commend(String cmd) {
		Runtime runtime = Runtime.getRuntime();
		try{
			Process process = runtime.exec(cmd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	public String commendSTT (String cmd) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(cmd);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line="";
		while(true){
			line = br.readLine();
			if(line.contains("junhee")){
				break;
			}
		}
		return line;
		
		}

}
