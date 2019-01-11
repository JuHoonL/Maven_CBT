package com.biz.cbt.vo;

public class CBTVO {

	private String cb_num;
	private String cb_que;
	private String cb_ex1;
	private String cb_ex2;
	private String cb_ex3;
	private String cb_ex4;
	private String cb_ans;
	
	
	
	public CBTVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CBTVO(String cb_num, String cb_que, String cb_ex1, String cb_ex2, String cb_ex3, String cb_ex4,
			String cb_ans) {
		super();
		this.cb_num = cb_num;
		this.cb_que = cb_que;
		this.cb_ex1 = cb_ex1;
		this.cb_ex2 = cb_ex2;
		this.cb_ex3 = cb_ex3;
		this.cb_ex4 = cb_ex4;
		this.cb_ans = cb_ans;
	}
	
	public CBTVO(String cb_que, String cb_ex1, String cb_ex2, String cb_ex3, String cb_ex4,
			String cb_ans) {
		super();
		this.cb_que = cb_que;
		this.cb_ex1 = cb_ex1;
		this.cb_ex2 = cb_ex2;
		this.cb_ex3 = cb_ex3;
		this.cb_ex4 = cb_ex4;
		this.cb_ans = cb_ans;
	}


	public String getCb_num() {
		return cb_num;
	}


	public void setCb_num(String cb_num) {
		this.cb_num = cb_num;
	}


	public String getCb_que() {
		return cb_que;
	}


	public void setCb_que(String cb_que) {
		this.cb_que = cb_que;
	}


	public String getCb_ex1() {
		return cb_ex1;
	}


	public void setCb_ex1(String cb_ex1) {
		this.cb_ex1 = cb_ex1;
	}


	public String getCb_ex2() {
		return cb_ex2;
	}


	public void setCb_ex2(String cb_ex2) {
		this.cb_ex2 = cb_ex2;
	}


	public String getCb_ex3() {
		return cb_ex3;
	}


	public void setCb_ex3(String cb_ex3) {
		this.cb_ex3 = cb_ex3;
	}


	public String getCb_ex4() {
		return cb_ex4;
	}


	public void setCb_ex4(String cb_ex4) {
		this.cb_ex4 = cb_ex4;
	}


	public String getCb_ans() {
		return cb_ans;
	}


	public void setCb_ans(String cb_ans) {
		this.cb_ans = cb_ans;
	}


	@Override
	public String toString() {
		return "CBTVO [cb_num=" + cb_num + ", cb_que=" + cb_que + ", cb_ex1=" + cb_ex1 + ", cb_ex2=" + cb_ex2
				+ ", cb_ex3=" + cb_ex3 + ", cb_ex4=" + cb_ex4 + ", cb_ans=" + cb_ans + "]";
	}
	
	
	
}
