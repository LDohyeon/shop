package admin;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;
import beans.Member;

@WebServlet("/adminmemberList.do")
public class AdminmemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MemberDAO mDAO=MemberDAO.getInstance();
		List<Member> list= mDAO.memberList();
		
		request.setAttribute("m", list);
		

		
		RequestDispatcher dispatcher=request.getRequestDispatcher("adminMemberList.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	
		String search= request.getParameter("search");
		MemberDAO mDAO=MemberDAO.getInstance();
		List<Member> list=mDAO.memberList(search);
		
		request.setAttribute("m", list);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("adminMemberList.jsp");
		dispatcher.forward(request, response);
		
		
	}

}










