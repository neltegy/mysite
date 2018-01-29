package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestBookVo;
import com.javaex.vo.UserBoardVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url, id, pw);
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " insert into board " + 
						" values( seq_board_no.nextval,?,?,sysdate,0,?)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			
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
	
	public List<UserBoardVo> getlist() {  //users 랑 board랑 join해서 모두 List<BoardVo>에 담아간다.
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<UserBoardVo> userboardlist = new ArrayList<UserBoardVo>();
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url,id,pw);
			// 3. SQL문 준비 / 바인딩 / 실행
				String query = " select u.no userno "+ //유저번호
								      " ,name "+
								      " ,email "+
								      " ,password "+
								      " ,b.no boardno "+ //게시판번호
								      " ,title "+
								      " ,content "+
								      " ,to_char(reg_date,' yyyy\"년\" mm\"월\" dd\"일\" hh\"시\" mm\"분\"') reg_date "+
								      " ,hit "+
								      " ,user_no "+//외래키
								" from users u "+
								    " ,board b "+
								" where u.no = b.user_no "+
								" order by reg_date desc ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				UserBoardVo userboardvo = new UserBoardVo();
				
				userboardvo.setUserno(rs.getInt("userno")); //유저번호
				userboardvo.setName(rs.getString("name"));
				userboardvo.setEmail(rs.getString("email"));
				userboardvo.setPassword(rs.getString("password"));
				userboardvo.setBoardno(rs.getInt("boardno")); //게시판번호
				userboardvo.setTitle(rs.getString("title"));
				userboardvo.setContent(rs.getString("content"));
				userboardvo.setReg_date(rs.getString("reg_date"));
				userboardvo.setHit(rs.getInt("hit"));
				userboardvo.setUser_no(rs.getInt("user_no"));
				
				userboardlist.add(userboardvo);
				
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
		
		return userboardlist;
	}
	
	
	public BoardVo getlist(int boardno) { //board의 정보
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo boardvo = null;
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url,id,pw);
			// 3. SQL문 준비 / 바인딩 / 실행
				String query = " select title "+
								      " ,content "+
								      " ,user_no "+
								      " ,no "+
								      " ,hit "+
							   " from board "+
							   " where no = ? ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, boardno);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			if(rs.next()) {
				boardvo = new BoardVo();
				
				boardvo.setTitle(rs.getString("title"));
				boardvo.setContent(rs.getString("content"));
				boardvo.setUser_no(rs.getInt("user_no"));
				boardvo.setNo(rs.getInt("no"));
				boardvo.setHit(rs.getInt("hit"));
				
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
		
		return boardvo;
		
	}
	
	
	public void update(BoardVo vo) { //글수정
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
			String query = " update board set "+
									" title = ?, "+
									" content = ? "+
									" where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			
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
	
	public void update_hit(BoardVo vo) { //조회수 업데이트
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
			String query = " update board set "+
									" hit = ? "+
									" where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, vo.getHit());
			pstmt.setInt(2, vo.getNo());
			
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
	
	public List<UserBoardVo> getkwdlist(String kwd) {  //users 랑 board랑 join해서 모두 List<BoardVo>에 담아간다. 검색기능
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<UserBoardVo> userboardlist = new ArrayList<UserBoardVo>();
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "webdb";
			String pw = "webdb";
			conn = DriverManager.getConnection(url,id,pw);
			// 3. SQL문 준비 / 바인딩 / 실행
				String query = " select u.no userno "+ //유저번호
								      " ,name "+
								      " ,email "+
								      " ,password "+
								      " ,b.no boardno "+ //게시판번호
								      " ,title "+
								      " ,content "+
								      " ,reg_date "+
								      " ,hit "+
								      " ,user_no "+//외래키
								" from users u "+
								    " ,board b "+
								" where u.no = b.user_no "+
								   " and title like ? "; // '%?%'
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, "%"+kwd+"%"); //
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				UserBoardVo userboardvo = new UserBoardVo();
				
				userboardvo.setUserno(rs.getInt("userno")); //유저번호
				userboardvo.setName(rs.getString("name"));
				userboardvo.setEmail(rs.getString("email"));
				userboardvo.setPassword(rs.getString("password"));
				userboardvo.setBoardno(rs.getInt("boardno")); //게시판번호
				userboardvo.setTitle(rs.getString("title"));
				userboardvo.setContent(rs.getString("content"));
				userboardvo.setReg_date(rs.getString("reg_date"));
				userboardvo.setHit(rs.getInt("hit"));
				userboardvo.setUser_no(rs.getInt("user_no"));
				
				userboardlist.add(userboardvo);
				
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
		
		return userboardlist;
	}
	
	public void delete(int no) { //boardno를 받아옴 // delete from board where no = 2;
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
			String query = " delete from board where no = ? ";
			
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
