package comment;

import java.io.IOException;


import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CommentDAO;



@WebServlet("/commentDelete.do")
public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pk =request.getParameter("pk");
		
		request.setAttribute("pk", pk);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("boardCommentDelete.jsp");
		dispatcher.forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int pk =Integer.parseInt(request.getParameter("pk"));

		CommentDAO cDAO=CommentDAO.getinstance();
		
		cDAO.commentDelete(pk);
		
		
		request.setAttribute("aTag", 0);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("boardCommentDelete.jsp");
		dispatcher.forward(request, response);
		
	}

}














