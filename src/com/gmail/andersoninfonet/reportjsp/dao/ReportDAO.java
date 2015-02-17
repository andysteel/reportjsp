package com.gmail.andersoninfonet.reportjsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.andersoninfonet.reportjsp.connection.ConnectionFactory;
import com.gmail.andersoninfonet.reportjsp.modelo.User;


public class ReportDAO {

	private Connection con;

	public ReportDAO() {
		
		con = new ConnectionFactory().getConnection();
	}
	
	public List<User> getUsuarios(){
		try{
			List<User> usuarios = new ArrayList<User>();
			PreparedStatement stmt = con.prepareStatement("select * from seguranca.users");
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				User user = new User();
				
				user.setUser_id(rs.getLong("user_id"));
				user.setUser_username(rs.getString("user_username"));
				user.setUser_password(rs.getString("user_password"));
				
				usuarios.add(user);
			}
			
			rs.close();
			stmt.close();
			
			System.out.println(usuarios.get(0).getUser_id());
			return usuarios;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
