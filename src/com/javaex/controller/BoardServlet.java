package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserBoardVo;
import com.javaex.vo.UserVo;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		if ("list".equals(actionName)) {
			
			String kwd = request.getParameter("kwd");
			String jsppageno2 = request.getParameter("jsppageno");
			
			System.out.println("jsppageno:"+jsppageno2);
			
			if(kwd != null) { //검색을 누르면 null이 아닌것으로 뜬다 검색을안누르고 새로고침하면 null로 나온다.
				BoardDao boardDao = new BoardDao();
				List<UserBoardVo> vo = boardDao.getkwdlist(kwd);
				
				//request.setAttribute에 vo 값을 넣어준다.
				request.setAttribute("rekwd", kwd);
				request.setAttribute("userboard_vo_list", vo);
			}else { //검색을 안누른경우
			
				//dao에서 getlist() 로 userboard리스트를 가져온다. (반환형은 List<userBoardVo>)
				BoardDao boardDao = new BoardDao();
				List<UserBoardVo> vo = boardDao.getlist();
				
				int postnum = vo.size();
				System.out.println("글의 갯수"+postnum);//List의 길이
				
				int pagenum =0;
				if(vo.size()%10 == 0) { //글의갯수가 10단위 초과될때부터 페이지를 생성하게하는 조건문
					pagenum = vo.size()/10;
				}else {
					pagenum = vo.size()/10 + 1;
				}
				
				System.out.println("페이지의 갯수"+pagenum);
				
				List<Integer> page = new ArrayList<Integer>();
				for(int i = 0; i < pagenum; i++) {
					page.add(i);
				}
				
				
				if(jsppageno2 == null || jsppageno2.equals("0") ) { //페이지번호를 안누른경우를 1페이지로 처리하게함 index로는  0
					//page.get(0);
					//최근 10개만 표시되게한다.
					List<UserBoardVo> firstvo = vo.subList(0, 10); //뒤의 index는 전값까지 넣어진다.
					
					List<Integer> fivepage = null;
					
					if(pagenum > 5) {
						fivepage = page.subList(0, 5);
						request.setAttribute("page", fivepage);
					}else {
						request.setAttribute("page", page);
					}
					
					request.setAttribute("userboard_vo_list", firstvo);
				}else { //페이지번호를누른경우
					int jsppageno = Integer.parseInt(jsppageno2);
					int pageindex = 0;
					int cnt = 0 ;
					
					/*int pagebasket = pagenum/5;
					int modpagebasket = 0;
					if(pagenum%5 != 0) {
						modpagebasket = 1;
					}*/
					
					List<Integer> fivepage = null;
					
					for(int i = 0 ; i < pagenum/5 + 1; i++) {
						if(i*5 <= jsppageno && jsppageno < i*5+5) { //페이지번호 5까지
							pageindex = cnt;
							System.out.println("pageindex:"+pageindex);// jsppageno == 4 일때 cnt = 0 이고 끝
							break;
						}else {
							System.out.println("cnt"+cnt);
							cnt++;
						}
					}
					
					
					// jsppageno 0~4 = 0 , jsppageno 5~9 = 1
					
					if(pageindex*5+5 > pagenum) {
						fivepage = page.subList(pageindex*5, pagenum);
					}else {
						fivepage = page.subList(pageindex*5, pageindex*5+5);
					}
					
					
					request.setAttribute("page", fivepage);
					// jsppageno = 0~4 page.(0~4) ,jsppageno = 5~9 page.(5~9) ,jsppageno = 10~ 14 page.(10~14)
					
					//page.get(jsppageno);
					List<UserBoardVo> elsevo = null;
					if(pagenum == jsppageno+1) { //jsppageno 는 index 값이다(0부터시작)
						elsevo = vo.subList(10*jsppageno, postnum);//12개인경우19개까지읽으면빈곳을읽기때문에글의갯수를넣었다
					}else {
						elsevo = vo.subList(10*jsppageno, (10*jsppageno)+10);
					}
															
					request.setAttribute("countpage", jsppageno);
					request.setAttribute("userboard_vo_list", elsevo);
					//jsppageno = 0 vo.(0~9) , jsppageno = 1 vo.(10~19) , jsppageno = 2 vo.(20~29)
				}
				
				
				request.setAttribute("pagenum", pagenum);
				request.setAttribute("rejsppageno", jsppageno2);
				request.setAttribute("postnum", postnum);
				
				//request.setAttribute("page", page);
				
				//request.setAttribute("userboard_vo_list", vo); //request.setAttribute에 vo 값을 넣어준다.
			}
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
			
		} else if ("modifyform".equals(actionName)) {
			System.out.println("modyfyform 진입");
			String no = request.getParameter("no"); //boardno 받아옴
			int boardno = Integer.parseInt(no);
			BoardDao dao = new BoardDao();
			BoardVo boardvo = dao.getlist(boardno); //title,content,user_no 값을 가져옴
			
			request.setAttribute("boardvo", boardvo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
		} else if ("modify".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser"); // 수정전 유저정보
			
			if (authUser == null) { // 비로그인상태

			} else { // 로그인상태
				
				String no = request.getParameter("boardno");
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				
				int boardno = Integer.parseInt(no);
				
				BoardVo vo = new BoardVo();
				vo.setNo(boardno);
				vo.setTitle(title);
				vo.setContent(content);
				
				BoardDao dao = new BoardDao();
				dao.update(vo);
				
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}
			
		} else if ("view".equals(actionName)) {
			//list.jsp에서 boardno를 넘겨줌
			String boardno = request.getParameter("boardno");
			int b_no = Integer.parseInt(boardno);
			//board의 제목이랑 내용을 getlist(String boardno)로 뿌려준다.
			BoardDao dao = new BoardDao();
			BoardVo vo = dao.getlist(b_no); //리턴타입은 BoardVo(title,content)
			
			BoardVo updatevo = new BoardVo();
			
			int hit = vo.getHit();
			hit++;
			
			updatevo.setNo(b_no);
			updatevo.setHit(hit);
			
			//BoardDao dao2 = new BoardDao();
			dao.update_hit(updatevo);
			
			request.setAttribute("boardno", b_no);
			request.setAttribute("boardvo", vo); //title이랑 content를 request에 set해줌
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
		} else if ("writeform".equals(actionName)) {
			HttpSession session = request.getSession();
			session.getAttribute("authUser"); // 수정전 유저정보
			if(session.getAttribute("authUser") == null) {
				
			}else {
				WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
			}
			
		} else if ("write".equals(actionName)) {
			System.out.println("write 진입성공");
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser"); // 수정전 유저정보

			// 세션에서 authUser no를 가져온다
			int no = authUser.getNo();
			// write.jsp에서 파라미터값 두개를 받아온다.
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// BoardVo를 생성한다.
			BoardVo boardvo = new BoardVo();

			boardvo.setNo(no);
			boardvo.setTitle(title);
			boardvo.setContent(content);

			// dao를 통해 board 테이블에 값들을 insert(BoardVo vo)시킨다
			BoardDao boarddao = new BoardDao();
			boarddao.insert(boardvo);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		}else if("delete".equals(actionName)) {
			System.out.println("delete 진입"); //성공
			String boardno = request.getParameter("no");// boardno받음
			int no = Integer.parseInt(boardno);
			System.out.println(no); //성공
			BoardDao dao = new BoardDao();
			dao.delete(no);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list");
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
