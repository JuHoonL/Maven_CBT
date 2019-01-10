package com.biz.cbt.vo;

import java.util.List;

public interface CBTDao {

	public List<CBTVO> selectAll();
	public CBTVO findByNum(String cb_num);
	public int insert(CBTVO vo);
	public int update(CBTVO vo);
	public int delete(String cb_num);
}
