package com.biz.cbt.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.biz.cbt.contract.CBTContract;
import com.biz.cbt.vo.CBTDao;

public class CBTSQLFactory {

	SqlSessionFactory sessionFactory;
	
	public CBTSQLFactory() {
		Properties props = new Properties();
		
		props.put("DRIVER", CBTContract.ORACLE_PRO.Driver);
		props.put("URL", CBTContract.ORACLE_PRO.Url);
		props.put("USER", CBTContract.ORACLE_PRO.User);
		
		props.put("PASSWORD", CBTContract.ORACLE_PRO.Password);
		
		CBTDataSourceFactory dataSourceFactory = new CBTDataSourceFactory();
		
		dataSourceFactory.setProperties(props);
		DataSource dataSource = dataSourceFactory.getDataSource();
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("StdEnv", transactionFactory,dataSource);
		
		Configuration config = new Configuration(environment);
		config.addMapper(CBTDao.class);
		
		this.sessionFactory = new SqlSessionFactoryBuilder().build(config);
	}
	
	public SqlSessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
}
