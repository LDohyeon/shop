package board;

import java.io.IOException;



import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.BoardDAO;
import beans.Board;
import java.util.*;

@WebServlet("/board.do")
public class BoardSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int listPage=Integer.parseInt(request.getParameter("listPage"));
		
		
		System.out.println("���� ������ : "+ startPage +", �� ���� �� ���� ������ ������(10��)" + listPage);
		
		
		BoardDAO bDAO=BoardDAO.getInstance();
		
		
		
		List<Board> data =bDAO.pageList(startPage, listPage);
		
		int pagebtn=bDAO.pagebtn();
		
		System.out.println("pagebtn :" + pagebtn);
		
		int page=pagebtn/listPage;
		
		if(page%listPage!=0)
		{
			page++;
		}
		
		System.out.println("page :" + page);
		

		
		request.setAttribute("data", data);
		
		
		request.setAttribute("startPage", startPage);
		request.setAttribute("listPage", listPage);
		
		request.setAttribute("page", page);
		

		RequestDispatcher dispatcher=request.getRequestDispatcher("board.jsp");
		dispatcher.forward(request, response);
		
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}









