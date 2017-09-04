package com.tim.ap.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileTool 구현 : 파일 관련 유틸
 * 
 * @author JunHyuk Choi (jhchoi@techinmotion.co.kr)
 */
public class FileTool {
	public static void move(String source, String destnation) throws IOException {
		if (!source.equals(destnation)) {
			File sourceFile = new File(source);
			File destnationFile = new File(destnation);

			if (destnationFile.exists() && destnationFile.isDirectory()) {
				throw new IOException("unable to move file " + " directory is exists " + destnationFile.getAbsolutePath());
			}

			if (destnationFile.exists() && destnationFile.isFile()) {
				destnationFile.delete();
			}

			if (!sourceFile.renameTo(destnationFile)) {
				throw new IOException("unable to move file " + sourceFile.getAbsolutePath() + " to " + destnationFile.getAbsolutePath());
			}
		}
	}

	public static void copy(String source, String destination) throws IOException {
		File sourceFile, destnationFile;

		sourceFile = new File(source);

		if (sourceFile.isFile() == false) {
			throw new IOException("not found file " + source);
		}

		if (sourceFile.canRead() == false) {
			throw new IOException("unable read file " + source);
		}

		destnationFile = new File(destination);

		if (destnationFile.exists()) {
			if (!destnationFile.isFile()) {
				String targetFileName = destination + File.separator + sourceFile.getName();
				destnationFile = new File(targetFileName);
			}
		}

		RandomAccessFile randomAccessFile = null;
		FileInputStream fileInputStream = null;
		FileChannel fileInputChannel = null;
		FileChannel fileOutputChannel = null;

		try {
			randomAccessFile = new RandomAccessFile(destnationFile, "rw");
			fileInputStream = new FileInputStream(sourceFile);
			fileInputChannel = fileInputStream.getChannel();
			fileOutputChannel = randomAccessFile.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(8192);

			while (fileInputChannel.read(byteBuffer) != -1) {
				byteBuffer.flip();
				fileOutputChannel.write(byteBuffer);
				byteBuffer.clear();
			}
		} finally {
			if (null != fileOutputChannel) {
				fileOutputChannel.close();
			}
			if (null != fileInputChannel) {
				fileInputChannel.close();
			}

			if (null != fileInputStream) {
				fileInputStream.close();
			}
			if (null != randomAccessFile) {
				randomAccessFile.close();
			}
		}
	}

	public static void delete(String filename) throws IOException {
		File file = new File(filename);
	
		if (!file.delete()) {
			throw new IOException("Can't delete file - check close: " + file);
		}
	}

	public static void checkExistsAndMakeDirectory(String directoryPath) throws IOException {
		File directory = new File(directoryPath);

		if (directory.exists() && !directory.isDirectory()) {
			if (!directory.delete()) {
				throw new IOException("unable to delete " + directoryPath);
			}
		}

		synchronized (FileTool.class) {
			if (!directory.exists()) {
				if (!directory.mkdirs()) {
					throw new IOException("unable to mkdir " + directoryPath);
				}
			}
		}
	}

	public static boolean isExistsDirectory(String directoryPath) {
		File directory = new File(directoryPath);

		if (null == directory || !directory.exists() || !directory.isDirectory()) {
			return false;
		}

		return true;
	}

	public static void makeDirectory(String directoryPath) throws IOException {
		File directory = new File(directoryPath);

		if (directory.exists()) {
			if (!directory.isDirectory() && !directory.delete()) {
				throw new IOException("unable to delete " + directoryPath);
			}
		} else {
			if (!directory.mkdir()) {
				throw new IOException("unable to mkdir " + directoryPath);
			}
		}
	}

	public static String load(String filePath) {
		char[] readByteLine = new char[1024];
		StringBuffer stringBuffer = new StringBuffer();

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);

			while (bufferedReader.read(readByteLine) != -1) {
				stringBuffer.append(readByteLine);
			}
		} catch (FileNotFoundException fnfe) {
			return null;
		} catch (IOException ioe) {
			return null;
		} finally {
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
				}
			} catch (IOException ignore) {
			}
			try {
				if (null != fileReader) {
					fileReader.close();
				}
			} catch (IOException ignore) {
			}

			bufferedReader = null;
			fileReader = null;
		}

		return stringBuffer.toString();
	}

	public static long getFileSize(String fileName) throws IOException {
		File file = new File(fileName);

		return file.length();
	}

	public static long getDirectorySize(String directory, boolean byRuntime) throws IOException {
		long fileSize = 0;

		if (byRuntime) {
			fileSize = getDirectorySizeByRuntime(directory);
		} else {
			fileSize = getDirectorySizeByFile(directory);
		}
		return fileSize;
	}

	public static long getDirectorySizeByFile(String directory) throws IOException {
		long fileSize = 0;
		java.io.File filePath = new java.io.File(directory);

		if (filePath.isDirectory()) {
			File files[] = filePath.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					fileSize += getDirectorySizeByFile(files[i].getAbsolutePath());
				} else {
					fileSize += getFileSize(files[i].getAbsolutePath());
				}
			}
		} else {
			fileSize = getFileSize(filePath.getAbsolutePath());
		}

		return fileSize;
	}

	public static long getDirectorySizeByRuntime(String directory) throws IOException {
		long fileSize = 0;

		try {
			String commandQuery = execRunTime("du -sk " + directory);
			StringBuffer stringBufffer = new StringBuffer();
			char[] c = commandQuery.toCharArray();

			for (int i = 0; i < c.length; i++) {
				if (c[i] == '\t') {
					break;
				} else {
					stringBufffer.append(c[i]);
				}
			}

			fileSize = Long.parseLong(stringBufffer.toString()) * 1024;
		} catch (Exception e) {
			fileSize = getDirectorySizeByFile(directory);
		}

		return fileSize;
	}

	private static String execRunTime(String command) throws Exception {
		Process process = null;

		process = Runtime.getRuntime().exec(command);
		process.waitFor();

		BufferedReader bufferedReader = null;
		String stringValue = "";

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReader.readLine();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
			}
		}

		return stringValue;
	}
}