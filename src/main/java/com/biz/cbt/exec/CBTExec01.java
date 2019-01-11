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
	
	public static void goQue() {
		es.makeQuestionList();
		while(true) {
			es.suffleQue();
			es.viewQuestion();
		}
	}

}
