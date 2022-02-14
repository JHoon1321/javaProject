package com.jdbc.project1;

import java.sql.Timestamp;

public class offerDTO {
	private int ordernum;
	private int no;
	private int ea;
	private String user_id;
	private String memo;
	private Timestamp regdate;
	private int index=20;
	

	public offerDTO() {
		super();
	}

	public offerDTO(int ordernum, int no, int ea, String user_id, String memo, Timestamp regdate) {
		super();
		this.ordernum = ordernum;
		this.no = no;
		this.ea = ea;
		this.user_id = user_id;
		this.memo = memo;
		this.regdate = regdate;
	}
	
	public int getIndex() {
		++index;
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getEa() {
		return ea;
	}

	public void setEa(int ea) {
		this.ea = ea;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "offerDTO [주문번호=" + ordernum + ", 상품코드=" + no + ", 수량=" + ea + ", 아이디=" + user_id + ", 메모="
				+ memo + ", 등록일=" + regdate + "]";
	}
	
	
	
	

}
