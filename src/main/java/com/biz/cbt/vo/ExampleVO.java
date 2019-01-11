package com.biz.cbt.vo;

public class ExampleVO {

	String strNum;			//PK값의 문제번호
	String strQue;			//문제를 저장할 변수
	String strExample1;		//보기1을 저장할 변수
	String strExample2;		//보기2을 저장할 변수
	String strExample3;		//보기3을 저장할 변수
	String strExample4;		//보기4을 저장할 변수
	String strAnswer;		//정답항목을 저장할 변수

	
	//기본 생성자
	public ExampleVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//필드생성자로 변수를 매개변수로 받아서 바로 변수에 저장가능
	public ExampleVO(String strNum, String strQue, String strExample1, String strExample2, String strExample3,
			String strExample4, String strAnswer) {
		super();
		this.strNum = strNum;
		this.strQue = strQue;
		this.strExample1 = strExample1;
		this.strExample2 = strExample2;
		this.strExample3 = strExample3;
		this.strExample4 = strExample4;
		this.strAnswer = strAnswer;
	}

	//getter&setter 
	public String getStrNum() {
		return strNum;
	}


	public void setStrNum(String strNum) {
		this.strNum = strNum;
	}


	public String getStrQue() {
		return strQue;
	}


	public void setStrQue(String strQue) {
		this.strQue = strQue;
	}


	public String getStrExample1() {
		return strExample1;
	}


	public void setStrExample1(String strExample1) {
		this.strExample1 = strExample1;
	}


	public String getStrExample2() {
		return strExample2;
	}


	public void setStrExample2(String strExample2) {
		this.strExample2 = strExample2;
	}


	public String getStrExample3() {
		return strExample3;
	}


	public void setStrExample3(String strExample3) {
		this.strExample3 = strExample3;
	}


	public String getStrExample4() {
		return strExample4;
	}


	public void setStrExample4(String strExample4) {
		this.strExample4 = strExample4;
	}


	public String getStrAnswer() {
		return strAnswer;
	}


	public void setStrAnswer(String strAnswer) {
		this.strAnswer = strAnswer;
	}


	//tostring
	public String toString() {
		return "ExampleVO [strNum=" + strNum + ", strQue=" + strQue + ", strExample1=" + strExample1 + ", strExample2="
				+ strExample2 + ", strExample3=" + strExample3 + ", strExample4=" + strExample4 + ", strAnswer="
				+ strAnswer + "]";
	}
	
}
