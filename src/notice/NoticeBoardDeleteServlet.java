package notice;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.NoticeBoardDAO;



@WebServlet("/noticeBoardDelete.do")
public class NoticeBoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
				
	
		NoticeBoardDAO nDAO=NoticeBoardDAO.getInstance();
	
		nDAO.noticeDelete(num);
		
		response.sendRedirect("noticeBoardSelect.do?startPage=1&lastPage=10");

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
