package com.biz.cbt.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

public class CBTDataSourceFactory implements DataSourceFactory {
	
	Properties props;
	
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub
		this.props = props;
	}

	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		PooledDataSource pds = new PooledDataSource();
		
		pds.setDriver(props.getProperty("DRIVER"));
		pds.setUrl(props.getProperty("URL"));
		pds.setUsername(props.getProperty("USER"));
		pds.setPassword(props.getProperty("PASSWORD"));
		
		return pds;
	}

}
