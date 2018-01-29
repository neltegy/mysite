package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;

public class GuestBookDao {
	public void insert(GuestBookVo vo) {
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
			String query = "INSERT INTO guestbook VALUES (seq_guest_no.nextval, ? , ? , ? , sysdate)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

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
	
	public void delete(int no) {
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
			String query = "delete from guestbook where no = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제완료");
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
	
	public List<GuestBookVo> getlist(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<GuestBookVo> gbvolist = new ArrayList<GuestBookVo>();
		
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
								      " ,password "+
								      " ,content "+
								      " ,reg_date "+
								" from guestbook "+
								" order by reg_date desc ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				GuestBookVo gbvo = new GuestBookVo();
				
				gbvo.setNo(rs.getInt("no"));
				gbvo.setName(rs.getString("name"));
				gbvo.setPassword(rs.getString("password"));
				gbvo.setContent(rs.getString("content"));
				gbvo.setReg_date(rs.getString("reg_date"));
				
				gbvolist.add(gbvo);
				
				//authorList.toString();
				//System.out.println(authorId+" "+authorName+" "+authorDesc);
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
		
		return gbvolist;
	}
}
