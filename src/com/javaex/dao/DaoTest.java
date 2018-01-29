package com.javaex.dao;

import java.util.List;

import com.javaex.vo.UserBoardVo;
import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {
		/*UserDao dao = new UserDao();
		UserVo vo = new UserVo("이름","ho2@naver.com","1234","male");
		dao.insert(vo);
		UserVo list = dao.getUser("ho@naver.com", "1234");
		System.out.println(list);*/
		
//		UserDao dao = new UserDao();
//		UserVo vo = new UserVo(1, "보라돌이","bo@naver.com", "1234", "male");
//		dao.update(vo);
		
//		BoardDao dao = new BoardDao();
//		System.out.println(dao.getlist(1));
		
//		BoardDao boardDao = new BoardDao();
//		List<UserBoardVo> vo = boardDao.getlist();
		
//		BoardDao dao = new BoardDao();
//		dao.delete(3);
		
//		BoardDao dao = new BoardDao();
//		List<UserBoardVo> list = dao.getlist();
//		for(UserBoardVo listTest : list) {
//			System.out.println(listTest);
//		}
		
//		BoardDao boardDao = new BoardDao();
//		List<UserBoardVo> vo = boardDao.getlist();
//		
//		List<UserBoardVo> firstvo = null;
//		
//		firstvo = vo.subList(0, 9);
//		
//		for(UserBoardVo listTest: firstvo) {
//			System.out.println(listTest);
//		}
		
		BoardDao dao = new BoardDao();
		List<UserBoardVo> list = dao.getlist();
		
		for(UserBoardVo listTest: list) {
			System.out.println(listTest);
		}
	}

}
