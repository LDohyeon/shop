package comment;

import java.io.*;


import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CommentDAO;

@WebServlet("/commentUpdate.do")
public class CommentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String comment=request.getParameter("commentUpdate");
		int pk=Integer.parseInt(request.getParameter("pk"));
		
		String num=request.getParameter("num");
		
		CommentDAO cDAO=CommentDAO.getinstance();
		cDAO.commentUpdate(pk, comment);
		
		response.sendRedirect("boardContent.do?num="+num);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}