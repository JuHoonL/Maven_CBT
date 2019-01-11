package com.biz.cbt.sql;

public class CBTSql {

	public static final String CBT_ALL = " SELECT * FROM tbl_cbt " ;
	public static final String CBT_FIND_NUM = " SELECT * FROM tbl_cbt WHERE cb_num = #{cb_num} ";
	
	public static final String CBT_INSERT 
	= " INSERT INTO tbl_cbt VALUES (#{cb_num},#{cb_que},#{cb_ex1},#{cb_ex2},#{cb_ex3},#{cb_ex4},#{cb_ans}) ";
	
	public static final String CBT_UPDATE 
	= " UPDATE tbl_cbt SET cb_que = #{cb_que}, cb_ex1 = #{cb_ex1}, cb_ex2 = #{cb_ex2}, "
	+ " cb_ex3 = #{cb_ex3}, cb_ex4 = #{cb_ex4}, cb_ans = #{cb_ans} WHERE cb_num = #{cb_num} ";
	
	public static final String CBT_DELETE = " DELETE FROM tbl_cbt WHERE cb_num = #{cb_num} ";
}
