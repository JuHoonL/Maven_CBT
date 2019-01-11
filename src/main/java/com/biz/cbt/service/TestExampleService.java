package com.biz.cbt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.biz.cbt.db.CBTSQLFactory;
import com.biz.cbt.vo.CBTDao;
import com.biz.cbt.vo.CBTVO;
import com.biz.cbt.vo.ExampleVO;
import com.biz.cbt.vo.SolutionVO;

public class TestExampleService {

	SqlSessionFactory sessionFactory;
	
	Scanner scan;
	
	List<CBTVO> cbtList;
	
	List<ExampleVO> qBankList;
	
	List<SolutionVO> solutionO;
	List<SolutionVO> solutionX;
	
	String stranswer ;
	
	
	
	public TestExampleService() {
		
		CBTSQLFactory cbtFactory = new CBTSQLFactory();
		
		this.sessionFactory = cbtFactory.getSessionFactory();
		
		scan = new Scanner(System.in);
		
		cbtList = new ArrayList();
		
		qBankList = new ArrayList();
		
		solutionO = new ArrayList();
		
		solutionX = new ArrayList();
	}
	
	
	
	public void makeQuestionList() {
		SqlSession session = sessionFactory.openSession();
		
		CBTDao dao = session.getMapper(CBTDao.class);
		
		cbtList = dao.selectAll();
		
		for(CBTVO vo : cbtList) {
			String strNum = vo.getCb_num();
			String strQue = vo.getCb_que();
			String[] str1 = vo.getCb_ex1().split(":");
			String strEx1OX = str1[0];
			String strExample1 = str1[1];
			String[] str2 = vo.getCb_ex2().split(":");
			String strEx2OX = str2[0];
			String strExample2 = str2[1];
			String[] str3 = vo.getCb_ex3().split(":");
			String strEx3OX = str3[0];
			String strExample3 = str3[1];
			String[] str4 = vo.getCb_ex4().split(":");
			String strEx4OX = str4[0];
			String strExample4 = str4[1];
			String strAnsOX = vo.getCb_ans();
		
			ExampleVO evo = 
					addZeroAndOne(vo, strExample1, strEx1OX, strExample2, strEx2OX, strExample3, strEx3OX, strExample4, strEx4OX, strAnsOX);
			
			qBankList.add(evo);
		}
	}
	
	
	
	public ExampleVO addZeroAndOne(CBTVO vo, String strExample1, String strEx1OX, 
			String strExample2, String strEx2OX, String strExample3, String strEx3OX, String strExample4, String strEx4OX, String strAnsOX) {
		
		ExampleVO evo = new ExampleVO();
		evo.setStrNum(vo.getCb_num());
		evo.setStrQue(vo.getCb_que());
		if(strEx1OX.equals(strAnsOX)) {
			evo.setStrExample1(strExample1 + ":0");
		}else {
			evo.setStrExample1(strExample1 + ":1");
		}
		if(strEx2OX.equals(strAnsOX)) {
			evo.setStrExample2(strExample2 + ":0");
		}else {
			evo.setStrExample2(strExample2 + ":1");
		}
		if(strEx3OX.equals(strAnsOX)) {
			evo.setStrExample3(strExample3 + ":0");
		}else {
			evo.setStrExample3(strExample3 + ":1");
		}
		if(strEx4OX.equals(strAnsOX)) {
			evo.setStrExample4(strExample4 + ":0");
		}else {
			evo.setStrExample4(strExample4 + ":1");
		}
		evo.setStrAnswer(strAnsOX);
		
		return evo;
	}
	
	
	
	
	public void view() {
		for(ExampleVO vo : qBankList) {
			System.out.println(vo);
		}
	}
	
	
	
	
	public void suffleQue() {
		for(ExampleVO vo : qBankList) {
			Collections.shuffle(qBankList);
		}
	}
	
	
	
	
	public int viewQuestion() {
		
		int index = 1;
		for(ExampleVO vo : qBankList) {
			int ret = this.question(vo, index);
			
			String strNextorEnd = reQue(ret,vo,index);
			
			if(index == 5) {
				viewList();
			}
			 
			if(strNextorEnd.equals("0")) {
				viewList();
				multiScore();
				return 0;
			}
			
			index++;
		}
		return 1;
	}
	
	
	
	public String reQue(int ret, ExampleVO vo, int index) {
		
		if(ret == 1) {
			SolutionVO svo = saveSolution(vo, index);
			solutionX.add(svo);
			System.out.println("(다시풀기:[1] / 다음문제:[enter]) >> ");
			String strReQue = scan.nextLine();
			if(strReQue.equals("1")) {
				this.question(vo, index);
			}
		}else {
			SolutionVO svo = saveSolution(vo, index);
			solutionO.add(svo);
			System.out.print("끝내기[0] / 다음문제풀기[enter] >> ");
			String strNextorEnd = scan.nextLine();
			
			return strNextorEnd;
		}
		return "";
		
	}
	
	
	
	public int question(ExampleVO vo, int index) {
		
		String[] strex = { vo.getStrExample1(), vo.getStrExample2(), vo.getStrExample3(), vo.getStrExample4() };
		Collections.shuffle(Arrays.asList(strex));
		System.out.println("====================================================================================");
		System.out.print(index + "> ");
		System.out.println(vo.getStrQue());
		System.out.println("------------------------------------------------------------------------------------");
		String[] strex1 = strex[0].split(":");
		System.out.println("①" + strex1[0]);
		String[] strex2 = strex[1].split(":");
		System.out.println("②" + strex2[0]);
		String[] strex3 = strex[2].split(":");
		System.out.println("③" + strex3[0]);
		String[] strex4 = strex[3].split(":");
		System.out.println("④" + strex4[0]);
		System.out.println("------------------------------------------------------------------------------------");
		System.out.print("정답 >>");
		stranswer = scan.nextLine();
		System.out.println("====================================================================================");
		
		int ret = retQuestion(stranswer, strex1[1], strex2[1], strex3[1], strex4[1]);
		
		return ret;
	}
	
	
	
	public int retQuestion(String stranswer, String strex1, String strex2, String strex3, String strex4) {
		
		int ret = 1;
		
		if(stranswer.equals("1")) {
			ret = answerOX(strex1);
		}
		if(stranswer.equals("2")) {
			ret = answerOX(strex2);
		}
		if(stranswer.equals("3")) {
			ret = answerOX(strex3);
		}
		if(stranswer.equals("4")) {
			ret = answerOX(strex4);
		}
		
		return ret;
	}
	
	
	
	public int answerOX(String answer) {
		if(answer.equals("0")) {
			System.out.println("정답입니다");
			System.out.println("====================================================================================");
			return 0;
		} else {
			System.out.println("오답입니다.");
			System.out.println("====================================================================================");
			return 1;
		}
	}
	
	public void distributeView(SolutionVO vo) {
		String[] strex = { vo.getStrExample1(), vo.getStrExample2(), vo.getStrExample3(), vo.getStrExample4() };
		System.out.println("====================================================================================");
		System.out.println(vo.getStrQue());
		String[] strex1 = strex[0].split(":");
		System.out.println("①" + strex1[0]);
		String[] strex2 = strex[1].split(":");
		System.out.println("②" + strex2[0]);
		String[] strex3 = strex[2].split(":");
		System.out.println("③" + strex3[0]);
		String[] strex4 = strex[3].split(":");
		System.out.println("④" + strex4[0]);
	}
	
	
	
	public SolutionVO saveSolution(ExampleVO vo, int index) {
		SolutionVO svo = new SolutionVO();
		svo.setIndex(index);
		svo.setStrQue(vo.getStrQue());
		svo.setStrExample1(vo.getStrExample1());
		svo.setStrExample2(vo.getStrExample2());
		svo.setStrExample3(vo.getStrExample3());
		svo.setStrExample4(vo.getStrExample4());
		svo.setStrAnswer(vo.getStrAnswer());
		
		return svo;
	}
	
	
	
	public void viewList() {
		
		System.out.println("------------------------------------------------------------------------------------");
		for(SolutionVO ovo : solutionO) {
			distributeView(ovo);
		}
		System.out.println("------------------------------------------------------------------------------------");
		
		for(SolutionVO xvo : solutionX) {
			distributeView(xvo);
		}
	}
	
	public void multiScore() {
		int count = solutionO.size();
		
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("점수 :  " + (count * 5));
		System.out.println("====================================================================================");
	}
}
