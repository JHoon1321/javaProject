package com.jdbc.project1;

import java.sql.Timestamp;

public class MemberDTO {
	private int no;
	private String c_id;
	private String c_name;
	private String pwd;
	private Timestamp regdate;
	
	public MemberDTO() {
		super();
	}
	
	public MemberDTO(int no, String c_id, String c_name, String pwd, Timestamp regdate) {
		super();
		this.no = no;
		this.c_id = c_id;
		this.c_name = c_name;
		this.pwd = pwd;
		this.regdate = regdate;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [no=" + no + ", 아이디=" + c_id + ", 회사명=" + c_name + ", 패스워드=" + pwd + ", 가입일=" + regdate
				+ "]";
	}

}
