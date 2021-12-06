package admin;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;
import beans.Member;

@WebServlet("/adminVipMember.do")
public class AdminVipMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int vip=Integer.parseInt(request.getParameter("page")); 
				
		
		
		MemberDAO mDAO=MemberDAO.getInstance();
		
		List<Member> list=mDAO.memberVip(vip);
		
		
		request.setAttribute("m", list);
		request.setAttribute("vip", vip);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("adminMemberList.jsp");
		dispatcher.forward(request, response);
		
	}

}
