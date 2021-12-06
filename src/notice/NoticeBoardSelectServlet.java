package notice;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.mysql.cj.xdevapi.Client;

import DAO.NoticeBoardDAO;
import beans.NoticeBoardDTO;



@WebServlet("/noticeBoardSelect.do")
public class NoticeBoardSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage=Integer.parseInt(request.getParameter("lastPage"));
		
		
		
		
		NoticeBoardDAO nDAO=NoticeBoardDAO.getInstance();
		
		int[] count=new int[4];
		count[0]=nDAO.opctionCount();
		count[1]=nDAO.opctionCount0();
		count[2]=nDAO.opctionCount1();
		count[3]=nDAO.opctionCount2();

		List<NoticeBoardDTO> fillList=nDAO.noticeList();
		
		List<NoticeBoardDTO> list=nDAO.noticeList(startPage, lastPage);
		

		
		
		int pageBtn=nDAO.pageBtn();
		
		int page=pageBtn/lastPage;
		
		if(pageBtn%lastPage !=0)
		{
			page++;
		}
		
		
		request.setAttribute("fill", fillList);
		
		request.setAttribute("list", list);
		request.setAttribute("startPage", startPage);
		request.setAttribute("lastPage", lastPage);
		
		request.setAttribute("count", count);
		
		request.setAttribute("page", page);
		
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("noticeBoard.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
