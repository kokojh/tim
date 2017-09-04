package com.tim.ap.entity;

public class MemberEntity {
	private int id;
	private String email;
	private String pw;
	private String name_last;
	private String name_first;
	private char role;
	private char auth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName_last() {
		return name_last;
	}

	public void setName_last(String name_last) {
		this.name_last = name_last;
	}

	public String getName_first() {
		return name_first;
	}

	public void setName_first(String name_first) {
		this.name_first = name_first;
	}

	public char getRole() {
		return role;
	}

	public void setRole(char role) {
		this.role = role;
	}

	public char getAuth() {
		return auth;
	}

	public void setAuth(char auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return new com.google.gson.Gson().toJson(this);
	}
}
