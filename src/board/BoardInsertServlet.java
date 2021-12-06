package board;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Board;
import DAO.BoardDAO;


@WebServlet("/boardIn.do")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("boardWriting.jsp");
		dispatcher.forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("utf-8");
		String url="board.do?startPage=1&&listPage=10";
		
		HttpSession session=request.getSession();

			
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String id=request.getParameter("id");
			
		Board bd=new Board();
			
		bd.setTitle(title);
		bd.setContent(content);
		bd.setId(id);
			
			
		BoardDAO bDAO=BoardDAO.getInstance();
			
		int result = bDAO.bdInsert(bd);
			
			
		if(result == 1)
		{
			request.setAttribute("BoardMeassage", "고객 센터에 글을 업로드하였습니다.");
				
		}
		else
		{
			request.setAttribute("BoardMeassage", "고객 센터에 글 업로드를 실패하였습니다..");
		}
			


		response.sendRedirect("board.do?startPage=1&&listPage=10");


	}

}














