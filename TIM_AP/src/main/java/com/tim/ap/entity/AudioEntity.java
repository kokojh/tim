package com.tim.ap.entity;

import org.springframework.web.multipart.MultipartFile;

public class AudioEntity {
	private MultipartFile multipartFile;
	private int id;
	private int c_id;
	private String m_email;
	private String time_beg;
	private String time_end;
	private String ad_text;
	private String ad_wav_filepath;
	private int ad_download_cnt;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public String getTime_beg() {
		return time_beg;
	}

	public void setTime_beg(String time_beg) {
		this.time_beg = time_beg;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getAd_text() {
		return ad_text;
	}

	public void setAd_text(String ad_text) {
		this.ad_text = ad_text;
	}

	public String getAd_wav_filepath() {
		return ad_wav_filepath;
	}

	public void setAd_wav_filepath(String ad_wav_filepath) {
		this.ad_wav_filepath = ad_wav_filepath;
	}

	public int getAd_download_cnt() {
		return ad_download_cnt;
	}

	public void setAd_download_cnt(int ad_download_cnt) {
		this.ad_download_cnt = ad_download_cnt;
	}

	@Override
	public String toString() {
		return new com.google.gson.Gson().toJson(this);
	}
}
