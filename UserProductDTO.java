package com.jdbc.project1;

import java.sql.Timestamp;

public class UserProductDTO {
	private int no;
	private String p_name;
	private int s_price;
	private int ea;
	private String thumbnail;
	private Timestamp regdate;
	
	//생성자
	public UserProductDTO() {
		super();
	}

	public UserProductDTO(int no, String p_name,int s_price, int ea, String thumbnail, Timestamp regdate) {
		super();
		this.no = no;
		this.p_name = p_name;
		this.s_price = s_price;
		this.ea = ea;
		this.thumbnail = thumbnail;
		this.regdate = regdate;
	}

	//getter/setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	
	public int getS_price() {
		return s_price;
	}

	public void setS_price(int s_price) {
		this.s_price = s_price;
	}
	
	public int getEa() {
		return ea;
	}

	public void setEa(int ea) {
		this.ea = ea;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}


	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
		return "BookDTO [no=" + no + ", 상품명=" + p_name +", 판매가=" + s_price +  ", 수량=" + ea
				+ ", 상품사진=" + thumbnail +", 상품등록일=" + regdate + "]";
	}




}
