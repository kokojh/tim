package com.tim.ap.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author			TechInMotion ,  송두영
 * date				20170825
 * description		file splitter, 파일을 읽어와 원하는 크기로 잘라주는 기능
 *
 */
public class FileSplitter {
	
	// 12MB 기준 자름, 1024 = KB, 1024 * 1024 = MB...
	public static long preferedSize = 1024 * 1024 * 12;
	
	
	/*public static void FileSplitter(File srcFile, String destDir) {
				
		//this.srcFile = srcFile;
		//this.destDir = destDir;
			
		split(preferedSize);
		
		//System.out.println("파일 자르기 들어옴");
		
	}*/

	//public static File srcFile;
	//public static String destDir;
	
	
	// 첫번째 파라미터 = 파일 위치, 두번째 파라미터 = 파일이 생성될 위치
	public static void split(File srcFile, String destDir) {
		
		
		
		//this.preferedSize = preferedSize;
		
		String fileName = srcFile.getName();
		long srcSize = srcFile.length();
		long quotient = srcSize / preferedSize;
		int fileNoSpace = getFileNumberSpace(quotient + 1);
		int fileNo = 0;
		int buffSize = 9096;
		try {
			String firstFileName = destDir + formatFileName(fileName, fileNo, fileNoSpace);
			FileOutputStream fos = new FileOutputStream(new File(firstFileName));
			System.out.println(firstFileName);
			OutputStream os = new BufferedOutputStream(fos);
			FileInputStream fis = new FileInputStream(srcFile);
			InputStream is = new BufferedInputStream(fis);
			byte buf[] = new byte[buffSize];
			int s = 0;
			long writtenSize = 0;
			while ((s = is.read(buf, 0, buffSize)) > 0) {
				if (writtenSize > preferedSize) {
					writtenSize = 0;
					fileNo++;
					os.close();
					String outputFileName = destDir + formatFileName(fileName, fileNo, fileNoSpace);
					System.out.println(outputFileName);
					os = new BufferedOutputStream(new FileOutputStream(new File(outputFileName)));
				}
				os.write(buf, 0, s);
				writtenSize = writtenSize + s;
			}
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getFileNumberSpace(long quotient) {
		if (quotient == 0) {
			return 1;
		}
		int space = 0;
		while (quotient > 0) {
			quotient = quotient / 10;
			space++;
		}
		return space;
	}
	
	// 파일명 끝에 붙는 단어
	/** 
	 * 아래처럼 결과 출력
	 * C:/Users/TechInMotion-2/Desktop/split/170820_sdy001_0.wav
	 * C:/Users/TechInMotion-2/Desktop/split/170820_sdy001_1.wav
	 * C:/Users/TechInMotion-2/Desktop/split/170820_sdy001_2.wav
	**/
	
	private static String formatFileName(String prefix, int fileNo, int fileNoSpace) {
		
		int space = fileNoSpace - getFileNumberSpace(fileNo);
		StringBuffer sb = new StringBuffer();
		
		// 파일명에 "."을 찾아서 서브스트링하는 기능
		// idx 변수에 prefix(파일명)의 "." 인덱스 값 저장
		int idx = prefix.indexOf(".");
		
		// word 스트링 변수에 파일명 substring 처음부터 "." 이 들어간 전부분까지 자름
		String word = prefix.substring(0, idx);
		
		//System.out.println(word);
		
		//파일명 뒤에 "_숫자" 증가
		sb.append(word + "_" + fileNo);
		//sb.append(prefix + "_" + fileNo);
		
		for (int i = 0; i < space; i++) {
			sb.append(0);
		}
		
		//확장자 .wav 붙는 기능 추가
		sb.append(".wav");
		
		return sb.toString();
	}

	/** * @param args *//*
	public static void main(String[] args) {
		FileSplitter splitter = new FileSplitter(new File ("C:/Users/TechInMotion-2/Desktop/video-201708211734.mp4"), "C:/Users/TechInMotion-2/Desktop/split/");
		//splitter.split(1024*1024*12);
	}*/
}
