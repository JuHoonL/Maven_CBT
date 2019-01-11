package com.biz.cbt.service;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.biz.cbt.db.CBTSQLFactory;
import com.biz.cbt.vo.CBTDao;
import com.biz.cbt.vo.CBTVO;

public class CBTService {

	SqlSessionFactory sessionFactory;
	Scanner scan;
	
	public CBTService() {
		scan = new Scanner(System.in);
		
		CBTSQLFactory cbtFactory = new CBTSQLFactory();
		
		this.sessionFactory = cbtFactory.getSessionFactory();
	}
	
	public void cbtView() {
		SqlSession session = this.sessionFactory.openSession();
		CBTDao dao = session.getMapper(CBTDao.class);
		
		List<CBTVO> cbtList = dao.selectAll();
		
		for(CBTVO vo : cbtList) {
			System.out.println(vo);
		}
	}
	
	public CBTVO cbtInfoInput() {
		System.out.print("번호입력(종료:0) >> ");
		String strNum = scan.nextLine();
		if(strNum.equals("0")) return null;
		
		System.out.print("문제입력 >> ");
		String strQue = scan.nextLine();
		
		System.out.print("보기1입력 >> ");
		String strEx1 = scan.nextLine();
		
		System.out.print("보기2입력 >> ");
		String strEx2 = scan.nextLine();
		
		System.out.print("보기3입력 >> ");
		String strEx3 = scan.nextLine();
		
		System.out.print("보기4입력 >> ");
		String strEx4 = scan.nextLine();
		
		System.out.print("정답입력 >> ");
		String strAns = scan.nextLine();
		
		CBTVO vo = new CBTVO(strNum,strQue,strEx1,strEx2,strEx3,strEx4,strAns);
		
		return vo;
	}
	
	public String viewScanData(CBTVO vo) {
		System.out.println("=============================================================================");
		System.out.println("                                입  력    정  보");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(vo.getCb_num() + vo.getCb_que());
		System.out.println(vo.getCb_ex1());
		System.out.println(vo.getCb_ex2());
		System.out.println(vo.getCb_ex3());
		System.out.println(vo.getCb_ex4());
		System.out.println("------------------");
		System.out.println(vo.getCb_ans());
		System.out.println("=============================================================================");
		
		System.out.print("입력하신 내용과 일치합니까(YES/NO)>> ");
		String strYN = scan.nextLine();
		
		return strYN ;
	}
	
	
	public void insert() {
		CBTVO vo = this.cbtInfoInput();
		if(vo == null) return ;
		
		String strYN = this.viewScanData(vo);
		
		if(strYN.equalsIgnoreCase("YES")) {
			SqlSession session = sessionFactory.openSession();
			CBTDao dao = session.getMapper(CBTDao.class);
			
			int ret = dao.insert(vo);
			
			session.commit();
			session.close();
			
			if(ret > 0) {
				System.out.println("추가입력이 완료되었습니다.");
			} else {
				System.out.println("추가입력이 완료되지못하였습니다.");
			}
		} else return ;
	}
	
	
	
}