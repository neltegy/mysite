package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.javaex.vo.UserVo;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class UserDao {
	
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url, id, pw);
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into users " + " values( seq_users_no.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 저장완료");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
	}
	
	public UserVo getUser(String email,String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVo uservo = null;
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url,id,pw);
			// 3. SQL문 준비 / 바인딩 / 실행
				String query = " select no "+
								      " ,name "+
								      " ,email "+
								      " ,gender "+
								" from users "+
								" where email = ? "+
								 " and password = ? ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			if (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				uservo = new UserVo(); //밖에다가 new를 해주게되면 UserVo의 필드들이 jvm에 의해서 
									   //int형이 자동으로 0 값이 들어가기때문에 값이 있게된다.
									   //rs.next()안에다가 해주면 값이 있기때문에 rs.next() 가 true가 되어 들어왔기 때문에 해도 된다.
				uservo.setNo(no);
				uservo.setName(name);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		return uservo;
	}
	
	public UserVo getUser(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVo uservo = null;
		
		try { 
			
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url,id,pw);
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " select no "+
							      " ,name "+
							      " ,email "+
							      " ,password "+
							      " ,gender "+
							" from users "+
							" where no = ? ";
			
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			if (rs.next()) {
				no = rs.getInt("no");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String gender = rs.getString("gender");
				
				uservo = new UserVo(); //밖에다가 new를 해주게되면 UserVo의 필드들이 jvm에 의해서 
									   //int형이 자동으로 0 값이 들어가기때문에 값이 있게된다.
									   //rs.next()안에다가 해주면 값이 있기때문에 rs.next() 가 true가 되어 들어왔기 때문에 해도 된다.
				uservo.setNo(no);
				uservo.setName(name);
				uservo.setEmail(email);
				uservo.setPassword(password);
				uservo.setGender(gender);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리

			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		return uservo;
	}
	
	public void update(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url,id,pw);
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " update users set "+
										" name = ?, "+
										" password = ?, "+
										" gender = ? "+
										" where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setInt(4, vo.getNo());
			
			int count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println(count + "건 변경완료");
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리

			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}
	
	
}
