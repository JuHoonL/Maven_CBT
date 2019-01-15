package com.biz.cbt.service;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.biz.cbt.dao.CBTDao;
import com.biz.cbt.db.CBTSQLFactory;
import com.biz.cbt.vo.CBTVO;

public class CBTService {

	SqlSessionFactory sessionFactory;
	Scanner scan;
	
	//생성자
	public CBTService() {
		scan = new Scanner(System.in);
		
		CBTSQLFactory cbtFactory = new CBTSQLFactory();
		
		this.sessionFactory = cbtFactory.getSessionFactory();
	}
	
	//전체 문제리스트를 보여주고 리턴값으로 그 리스트를 리턴해주는 메서드
	public List<CBTVO> cbtView() {
		SqlSession session = this.sessionFactory.openSession();
		CBTDao dao = session.getMapper(CBTDao.class);
		
		List<CBTVO> cbtList = dao.selectAll();
		
		for(CBTVO vo : cbtList) {
			System.out.println(vo);
		}
		
		return cbtList;
	}
	
	//해당 문제번호를 받아서 그 번호의 VO를 보여주고 그 번호의 VO를 리턴해주는 메서드
	public CBTVO cbtfindByNum(String cb_num) {
		SqlSession session = this.sessionFactory.openSession();
		CBTDao dao = session.getMapper(CBTDao.class);
		
		CBTVO cbtvo = dao.findByNum(cb_num);
		
		return cbtvo;
	}
	
	//문제추가시(INSERT) 각 항목을 스캔받아서 VO로 저장해서 리턴해주는 메서드
	public CBTVO cbtInfoInput() {
		System.out.print("번호입력(종료:0) >> ");
		String strNum = scan.nextLine();
		if(strNum.equals("0")) return null;
		
		System.out.print("문제입력 >> ");
		String strQue = scan.nextLine();
		
		System.out.print("보기1입력(1:내용) >> ");
		String strEx1 = scan.nextLine();
		
		System.out.print("보기2입력(2:내용) >> ");
		String strEx2 = scan.nextLine();
		
		System.out.print("보기3입력(3:내용) >> ");
		String strEx3 = scan.nextLine();
		
		System.out.print("보기4입력(4:내용) >> ");
		String strEx4 = scan.nextLine();
		
		System.out.print("정답입력 >> ");
		String strAns = scan.nextLine();
		
		CBTVO vo = new CBTVO(strNum,strQue,strEx1,strEx2,strEx3,strEx4,strAns);
		
		return vo;
	}
	
	//vo를 매개변수로 받아서 그 vo를 양식에 맞게 콘솔에 출력하는 메서드
	public String viewScanData(CBTVO vo) {
		System.out.println("=============================================================================");
		System.out.println("                                입  력    정  보");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("문제입력 >> " + vo.getCb_que());
		System.out.println("답안1번 >> " + vo.getCb_ex1());
		System.out.println("답안2번 >> " + vo.getCb_ex2());
		System.out.println("답안3번 >> " + vo.getCb_ex3());
		System.out.println("답안4번 >> " + vo.getCb_ex4());
		System.out.println("------------------");
		System.out.println("정답 문항 >> " + vo.getCb_ans());
		System.out.println("=============================================================================");
		
		System.out.print("위 내용 맞습니까?(YES/NO)>> ");
		String strYN = scan.nextLine();
		
		return strYN ;
	}
	
	//위의 스캐너로 받은 vo를 리턴해주는 메서드를 vo에 저장해서 아래의 확인 작업의 양식메서드를
	//거쳐서 yes를 입력하면 해당 vo를 추가(INSERT)해주는 메서드
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
	
	//번호를 입력받아서 해당 번호의 vo를 cbtfindByNum메서드를 이용해서 찾고
	//리턴 받은 그 vo가 맞는지 확인 메서드를 거쳐서 yes이면 inputupdate()라는 메서드를
	//이용해서 바꾸고 싶은 해당항목만 변경해서 update하는 메서드
	public void update() {
		
		System.out.print("번호입력(종료:0) >> ");
		String cb_num = scan.nextLine();
		if(cb_num.equals("0")) return ;
		
		CBTVO cbtvo = this.cbtfindByNum(cb_num);
		if(cbtvo == null) return ;
		
		String strYN = this.viewScanData(cbtvo);
		
		if(strYN.equalsIgnoreCase("YES")) {
			SqlSession session = sessionFactory.openSession();
			CBTDao dao = session.getMapper(CBTDao.class);
			
			CBTVO vo = this.inputupdate(cbtvo);
			int ret = dao.update(vo);
			
			session.commit();
			session.close();
			
			if(ret > 0) {
				System.out.println("추가입력/변경이 완료되었습니다.");
			} else {
				System.out.println("추가입력/변경이 완료되지못하였습니다.");
			}
		} else return ;
	}
	
	//업데이트 메서드에서 사용할 항목선택 하여 해당 항목만 변경하여
	//마지막에 9번끝내기를 누르면 vo를 리턴해주는 메서드
	public CBTVO inputupdate(CBTVO vo) {
		
		CBTVO cbvo = vo;
		
		while(true) {
			System.out.println("변경을 원하시는 항목을 선택해주세요");
			System.out.println("문제(0) / 보기1(1) / 보기2(2) / 보기3(3) / 보기4(4) / 정답(5) / 끝내기(9)");
			System.out.print(">> ");
			String strmenu = scan.nextLine();
			if(strmenu.equals("9")) return cbvo;
			
			if(strmenu.equals("0")) {
				System.out.print("문제입력 >> ");
				String strQue = scan.nextLine();
				cbvo.setCb_que(strQue);
				continue;
			}
			
			if(strmenu.equals("1")) {
				System.out.print("보기1입력(1:내용) >> ");
				String strEx1 = scan.nextLine();
				cbvo.setCb_ex1(strEx1);
				continue;
			}
			
			if(strmenu.equals("2")) {
				System.out.print("보기2입력(2:내용) >> ");
				String strEx2 = scan.nextLine();
				cbvo.setCb_ex2(strEx2);
				continue;
			}
			
			if(strmenu.equals("3")) {
				System.out.print("보기3입력(3:내용) >> ");
				String strEx3 = scan.nextLine();
				cbvo.setCb_ex3(strEx3);
				continue;
			}
			
			if(strmenu.equals("4")) {
				System.out.print("보기4입력(4:내용) >> ");
				String strEx4 = scan.nextLine();
				cbvo.setCb_ex4(strEx4);
				continue;
			}
			
			if(strmenu.equals("5")) {
				System.out.print("정답입력 >> ");
				String strAns = scan.nextLine();
				cbvo.setCb_ans(strAns);
				continue;
			}
		} 
	}
	
	//번호를 입력받아서 해당 vo를 찾아서 삭제해주는 메서드
	public void delete() {
		System.out.print("번호입력(종료:0) >> ");
		String strNum = scan.nextLine();
		if(strNum.equals("0")) return;
		
		CBTVO cbtvo = this.cbtfindByNum(strNum);
		if(cbtvo == null) return ;
		
		String strYN = this.viewScanData(cbtvo);
		
		if(strYN.equalsIgnoreCase("YES")) {
			SqlSession session = sessionFactory.openSession();
			CBTDao dao = session.getMapper(CBTDao.class);
			
			int ret = dao.delete(strNum);
			
			session.commit();
			session.close();
			
			if(ret > 0) {
				System.out.println("삭제가 완료되었습니다.");
			} else {
				System.out.println("삭제가 완료되지못하였습니다.");
			}
		}
	}
}
