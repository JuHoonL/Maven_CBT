package com.biz.cbt.vo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biz.cbt.sql.CBTSql;

public interface CBTDao {

	@Select(CBTSql.CBT_ALL)
	public List<CBTVO> selectAll();
	@Select(CBTSql.CBT_FIND_NUM)
	public CBTVO findByNum(String cb_num);
	
	@Insert(CBTSql.CBT_INSERT)
	public int insert(CBTVO vo);
	
	
	@Update(CBTSql.CBT_UPDATE)
	public int update(CBTVO vo);
	@Delete(CBTSql.CBT_DELETE)
	public int delete(String cb_num);
}
