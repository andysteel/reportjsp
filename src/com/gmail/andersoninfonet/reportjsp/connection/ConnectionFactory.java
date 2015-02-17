package com.gmail.andersoninfonet.reportjsp.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection() {
		
		try{
			DriverManager.registerDriver(new org.postgresql.Driver());
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/spring", "root", "502010");
		}catch(SQLException e){
			throw new RuntimeException();
		}
		
	}
}
