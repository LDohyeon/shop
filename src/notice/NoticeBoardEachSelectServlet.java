package notice;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.NoticeBoardDAO;
import beans.NoticeBoardDTO;



@WebServlet("/noticeBoardEachSelect.do")
public class NoticeBoardEachSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int num=Integer.parseInt(request.getParameter("num"));
		
		NoticeBoardDAO nDAO=NoticeBoardDAO.getInstance();
		
		NoticeBoardDTO nDTO=nDAO.noticeEachSelect(num);
		
		
		request.setAttribute("list", nDTO);
		request.setAttribute("num", num);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("noticeBoardEachSelect.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}







