package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		if("joinform".equals(actionName)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
		}else if("join".equals(actionName)) {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo uservo = new UserVo(name,email,password,gender);
			UserDao userdao = new UserDao();
			userdao.insert(uservo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinsuccess.jsp");
			
		}else if("loginform".equals(actionName)) {
			
			WebUtil.forward(request, response, "WEB-INF/views/user/loginform.jsp");
		}else if("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			System.out.println(email+" "+password);
			
			UserDao userdao = new UserDao();
			UserVo uservo = userdao.getUser(email, password);
			
			if(uservo == null) {
				System.out.println("로그인실패");
				WebUtil.redirect(request, response, "/mysite/user?a=loginform&result=fail");
			}else {
				System.out.println("로그인성공");
				
				HttpSession session = request.getSession(true);
				session.setAttribute("authUser", uservo);
				
				WebUtil.redirect(request, response, "/mysite/main");
			}
			
		}else if("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
		}else if("modifyform".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser"); //수정전 유저정보
			
			//no가 없으면 로그인폼으로 이동 (리다이렉트)
			if(authUser == null) { //비로그인상태
				
			}else { // 로그인상태
				int no = authUser.getNo();
				
				//데이터 가져옴
				UserDao userdao = new UserDao();
				UserVo uservo = userdao.getUser(no);
				
				//데이터 request에 저장
				request.setAttribute("userVo", uservo); //modifyform.jsp 의 text 란에 value 값을 채워주려고 셋해줌
				
				WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
			}
			
			
		}else if("modify".equals(actionName)) {
			System.out.println("modify 진입");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser"); //수정전 유저정보
			
			if(authUser == null) {
				
			}else {
				int no = authUser.getNo();
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String gender = request.getParameter("gender");
				
				UserVo uservo = new UserVo(no,name,"",password,gender); //수정할정보를 가지고 vo를 만듬
				UserDao userdao = new UserDao();
				userdao.update(uservo); //유저 정보가 업데이트됨
				
				authUser.setName(name); //세션에다가 변경된 값을 셋해줌
				
			}
			
			WebUtil.redirect(request, response, "/mysite/main");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
