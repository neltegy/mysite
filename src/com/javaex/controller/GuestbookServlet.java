package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;


@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		if("list".equals(actionName)) {
			System.out.println("list 진입");
			
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getlist();
			
			request.setAttribute("list", list);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
		}else if("add".equals(actionName)) {
			System.out.println("add 진입");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestBookVo vo = new GuestBookVo(name,password,content);
			GuestBookDao dao = new GuestBookDao();
			dao.insert(vo);
			
			WebUtil.redirect(request, response, "/mysite/gb?a=list");
		}else if("deleteform".equals(actionName)) {
			System.out.println("deleteform 진입");
			
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
			
		}else if("delete".equals(actionName)) {
			System.out.println("delete 진입");
			
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			int no2 = Integer.parseInt(no);
			
			GuestBookDao dao = new GuestBookDao();
			
			List<GuestBookVo> gblist = dao.getlist();
			for(GuestBookVo str : gblist){
				if(str.getNo() == no2){
					if(str.getPassword().equals(password)){
						dao.delete(no2);
					}
				}
				
			}
			
			WebUtil.redirect(request, response, "/mysite/gb?a=list");
			
		}else {
			System.out.println("잘못입력하셨습니다.");
			response.setContentType("text/html;charset=utf-8"); // 어떤 타입으로 출력할것인지 명시하였다.
			PrintWriter out = response.getWriter(); // getWriter() 출력스트림. 응답할 정보를 갖고 있는 
												// 객체에 출력스트림을 써서 out 객체에 담았다.
			out.println("<html>");
			out.println("<body>");
			out.println("<h1>잘못입력하셨습니다.</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
