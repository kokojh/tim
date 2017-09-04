package com.tim.ap.util;

import java.util.Random;

/**
 * Unique File ID Generator 정의 : 시간+PID+유일값(밀리세컨드 단위)
 * 
 * @author JunHyuk Choi (jhchoi@techinmotion.co.kr)
 */
public class UniqueFileIdGenerator {
	private static Object lock = new Object();
	private static int count = 0;
	private static final int defaultMaxCount = 99;

	public static String getUniqueFileId() {
		StringBuffer buf = new StringBuffer(64);

		buf.append(getTimeInMillis());
		buf.append(".");
		buf.append(getPID());
		buf.append(".");
		buf.append(getUniqueNumber());

		return buf.toString();
	}

	private static long getTimeInMillis() {
		return System.currentTimeMillis();
	}

	private static int getPID() {
		synchronized (lock) {
			if (count > defaultMaxCount) {
				count = 0;
			}

			return count++;
		}
	}

	private static int getUniqueNumber() {
		return Math.abs(new Random(System.currentTimeMillis()).nextInt() % 64);
	}
}