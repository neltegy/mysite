package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.chat.*;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

/**
 * Servlet implementation class ChattingServlet
 */
@WebServlet("/chat")
public class ChattingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChattingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter("a");
		
		if("chatform".equals(actionName)) {
			
			System.out.println("chat list 진입");
			
			HttpSession session = request.getSession();
			session.getAttribute("authUser");
			
			if(session.getAttribute("authUser") == null) {
				
			}else {
				
			}
			/*TcpServerTest server = new TcpServerTest();
			TcpServerTest.main(null);*/
				
			//request.setAttribute(arg0, arg1);
				
			
			WebUtil.forward(request, response, "/WEB-INF/views/chat/chat.jsp");
		}else if("chat".equals(actionName)) {
			System.out.println("chat 진입");
			
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
