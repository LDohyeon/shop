package board;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


import DAO.BoardDAO;


@WebServlet("/boardUpdate.do")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url="boardContent.do";
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String num=request.getParameter("num");
		
		BoardDAO bDAO=BoardDAO.getInstance();
		
		bDAO.boardUpdate(title, content, num);
		
		request.setAttribute(num, "num");
		
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
