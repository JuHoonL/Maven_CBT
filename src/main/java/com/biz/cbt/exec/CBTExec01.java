package com.biz.cbt.exec;

import java.util.Scanner;

import com.biz.cbt.service.CBTService;
import com.biz.cbt.service.ExampleService;

public class CBTExec01 {
	static CBTService cs ;
	static ExampleService es ;
	static Scanner scan ;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		cs = new CBTService();
		es = new ExampleService();
		scan = new Scanner(System.in);
		
		// 최상위 메인 메서드
		while(true) {
			System.out.println("====================================================================================");
			System.out.println("                            정보처리 기사 문제 뱅크 v 1.0");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("1.문제입력      2. 문제풀이      0.종료");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.print(">> ");
			String strMenu = scan.nextLine();
			int intMenu = Integer.valueOf(strMenu);
			
			if(intMenu == 0) return;
			if(intMenu == 1) insertQue();
			if(intMenu == 2) goQue();
		}
		
	}
	
	//문제입력을 선택시 나오는 문제입력메인 메서드
	public static void insertQue() {
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("1.문제등록      2. 문제수정      3. 문제삭제      0.종료");
		System.out.println("------------------------------------------------------------------------------------");
		System.out.print(">> ");
		String strMenu = scan.nextLine();
		int intMenu = Integer.valueOf(strMenu);
		if(intMenu == 0) return;
		
		if(intMenu == 1) cs.insert();
		if(intMenu == 2) cs.update();
		if(intMenu == 3) cs.delete();
	}
	
	//문제풀이 선택시 문제풀이를 위해서 Example서비스에서 메서드를 가져와서 실행하는 메서드
	public static void goQue() {
		es.makeQuestionList();
		while(true) {
			es.suffleQue();
			int ret = es.viewQuestion();
			if(ret == 0) 
				return;
			
		}
	}

}
