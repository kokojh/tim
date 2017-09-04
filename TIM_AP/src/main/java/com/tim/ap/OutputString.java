package com.tim.ap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;

public class OutputString {
	
	public  String output(String mlfpath){
		
		String output_line="";
		String output_cont = "";
		String result="";
		File mlf= new File(mlfpath);
		
		while(true){
			if(mlf.exists()){
				break;
			}
		}
		
			BufferedReader br = null;
			boolean flag= true;
			try {
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(mlf), "euc-kr"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					
					while(br.ready()){
						output_line= br.readLine();
						if(output_line.contains("MLF")||output_line.contains("stt_data/test.pcm") || output_line.contains("<s>")){
							output_line="";
						}
						output_cont+=output_line+" ";
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
				return output_cont;

		
	}
}
