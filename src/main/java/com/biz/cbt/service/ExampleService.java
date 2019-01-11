package com.biz.cbt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.biz.cbt.dao.CBTDao;
import com.biz.cbt.db.CBTSQLFactory;
import com.biz.cbt.vo.CBTVO;
import com.biz.cbt.vo.ExampleVO;
import com.biz.cbt.vo.SolutionVO;

public class ExampleService {

	SqlSessionFactory sessionFactory;
	
	Scanner scan;
	
	List<CBTVO> cbtList;		//db에서받아온 리스트
	
	List<ExampleVO> qBankList;	//cbt리스트를 재저장할 리스트
	
	List<SolutionVO> solutionO;	//해답 해설을 위한 리스트(정답리스트)
	List<SolutionVO> solutionX;	//해답 해설을 위한 리스트(오답리스트)
	
	String stranswer ;		// 스캐너에서 받을 정답항목 스트링 변수
	
	
	
	//생성자부분
	public ExampleService() {
		
		CBTSQLFactory cbtFactory = new CBTSQLFactory();
		
		this.sessionFactory = cbtFactory.getSessionFactory();
		
		scan = new Scanner(System.in);
		
		cbtList = new ArrayList();
		
		qBankList = new ArrayList();
		
		solutionO = new ArrayList();
		
		solutionX = new ArrayList();
	}
	
	
	
	//ctb리스트에서 각각항목을 빼서 새로운 QBank리스트에 재정리하는 메서드
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
	
	
	
	//채점을 위해서 정답항목을 비교할 때 혹시모를 오류를 방지하기위해서 보기1~4에 새로운 한가지의 짧은문자를 
	//넣어주어 exampleVO에 저장하고 그 vo를 리턴해주는 메서드 
	public ExampleVO addZeroAndOne(CBTVO vo, String strExample1, String strEx1OX, 
			String strExample2, String strEx2OX, String strExample3, String strEx3OX,
			String strExample4, String strEx4OX, String strAnsOX) {
		
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
	
	
	
	
	//qBankList를 콘솔에 출력하여 보여주는 메서드
	public void view() {
		for(ExampleVO vo : qBankList) {
			System.out.println(vo);
		}
	}
	
	
	
	//qBankList의 개수만큼 리스트를 섞어주는 메서드
	public void suffleQue() {
		for(ExampleVO vo : qBankList) {
			Collections.shuffle(qBankList);
		}
	}
	
	
	
	
	//문제풀이를 시작하는 메서드(리턴값 0을 리턴해주면 메인에서 문제풀이를 종료할 수 있게 리턴값을 설정해줌)
	public int viewQuestion() {
		
		int index = 1;
		for(ExampleVO vo : qBankList) {
			int ret = this.question(vo, index);
			
			String strNextorEnd = reQue(ret,vo,index);
			
			if(index == 5) {
				viewList();
				System.out.print("끝내기[0] / 다음문제풀기[enter] >> ");
				strNextorEnd = scan.nextLine();
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
	
	
	
	
	//오답검사용변수 ret와 vo, 순번의 index변수를 받아서 각각 코드를 실행하고 각각 정답과 오답에 따라서 
	//각각의 리스트에 저장하는 메서드(리턴값으로 끝내기 0의 문자열을 받아서 문제풀이를 끝낼때 사용하고자함)
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
	
	
	
	//Examplevo와 index변수를 받아서 문제를 양식에 맞게 콘솔에 표시해주는 메서드(오답 검사용 변수ret을 리턴하게 설계)
	public int question(ExampleVO vo, int index) {
		
		String[] strex 
		= { vo.getStrExample1(), vo.getStrExample2(), vo.getStrExample3(), vo.getStrExample4() };
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
	
	
	
	//SolutionVO와 순번변수index를 받아서 나중에코드추가를 통해서 오답정리때 들어갈 메서드
	public int question(SolutionVO vo, int index) {
		
		String[] strex = { vo.getStrExample1(), vo.getStrExample2(), 
							vo.getStrExample3(), vo.getStrExample4() };
		Collections.shuffle(Arrays.asList(strex));
		System.out.println("====================================================================================");
		System.out.print(index + "> ");
		System.out.println(vo.getStrQue());
		System.out.println("------------------------------------------------------------------------------------");
		String[] strex1 = strex[0].split(":");
		System.out.println("① " + strex1[0]);
		String[] strex2 = strex[1].split(":");
		System.out.println("② " + strex2[0]);
		String[] strex3 = strex[2].split(":");
		System.out.println("③ " + strex3[0]);
		String[] strex4 = strex[3].split(":");
		System.out.println("④ " + strex4[0]);
		System.out.println("------------------------------------------------------------------------------------");
		System.out.print("정답 >>");
		stranswer = scan.nextLine();
		System.out.println("====================================================================================");
		
		int ret = retQuestion(stranswer, strex1[1], strex2[1], strex3[1], strex4[1]);
		
		return ret;
	}
	
	
	
	//채점 메서드(정답이면 ret변수에 0을 리턴하고 오답이면 1을 리턴)
	public int retQuestion(String stranswer, String strex1, 
			String strex2, String strex3, String strex4) {
		
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
	
	
	
	//위의 채점 메서드에 들어갈 정답/오답 메서드
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

	
	
	//해설의 정답과 오답리스트를 위해서 ExampleVO에서 각각의 항목을 불러와서 SolutionVO의항목에 맞게 저장하는 메서드 
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
	
	
	
	//정답 오답 리스트중에서 문제를 상세하게 보고싶은 경우 사용할 메서드에 들어갈 콘솔출력양식 메서드
	public void distributeView(SolutionVO vo) {
		String[] strex = { vo.getStrExample1(), vo.getStrExample2(), vo.getStrExample3(), vo.getStrExample4() };
		System.out.println("====================================================================================");
		System.out.println(vo.getStrQue());
		String[] strex1 = strex[0].split(":");
		System.out.println("① " + strex1[0]);
		String[] strex2 = strex[1].split(":");
		System.out.println("② " + strex2[0]);
		String[] strex3 = strex[2].split(":");
		System.out.println("③ " + strex3[0]);
		String[] strex4 = strex[3].split(":");
		System.out.println("④ " + strex4[0]);
	}
	
	
	
	//정답/오답의 번호와 각각의 문항수를 콘솔에 출력하는 메서드
	public void viewList() {
		
		System.out.println("------------------------------------------------------------------------------------");
		for(SolutionVO ovo : solutionO) {
			System.out.print(ovo.getIndex() + " ");
		}
		int sizeO = solutionO.size();
		System.out.println();
		System.out.println("맞은 문항수 : " + sizeO);
		System.out.println("------------------------------------------------------------------------------------");
		
		for(SolutionVO xvo : solutionX) {
			System.out.print(xvo.getIndex() + " ");
		}
		int sizeX = solutionX.size();
		System.out.println();
		System.out.println("틀린 문항수 : " + sizeX);
	}
	
	
	
	//문제풀이가 끝나고 점수계산하는 메서드
	public void multiScore() {
		int count = solutionO.size();
		
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("점수 :  " + (count * 5));
		System.out.println("====================================================================================");
	}
}
