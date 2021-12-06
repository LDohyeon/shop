package notice;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.NoticeBoardDAO;
import beans.NoticeBoardDTO;



@WebServlet("/noticeBoardSelect2.do")
public class NoticeBoardSelectServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage=Integer.parseInt(request.getParameter("lastPage"));
		
		int op=Integer.parseInt(request.getParameter("op"));
		
		
		NoticeBoardDAO nDAO=NoticeBoardDAO.getInstance();

		int[] count=new int[4];
		count[0]=nDAO.opctionCount();
		count[1]=nDAO.opctionCount0();
		count[2]=nDAO.opctionCount1();
		count[3]=nDAO.opctionCount2();
		
		List<NoticeBoardDTO> fillList=nDAO.noticeList();
		
		List<NoticeBoardDTO> list=null;
		
		
		if(op==0)
		{
			list=nDAO.noticeList(startPage, lastPage);//필독 선택
		}
		else
		{
			list=nDAO.noticeList(startPage, lastPage, op);//공지와 이벤트 선택
		}
		
		
		int pageBtn=nDAO.pageBtn(op);
		
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
		
		request.setAttribute("op", op);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("noticeBoard.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
