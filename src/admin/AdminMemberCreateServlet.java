package admin;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;

@WebServlet("/adminMemberCreate.do")
public class AdminMemberCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("adminMemberCreate.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String year=request.getParameter("year");
		int monCheck=Integer.parseInt(request.getParameter("monCheck"));
		
		MemberDAO mDAO=MemberDAO.getInstance();
		
		String sqlValue="";
		
		
		
		if(monCheck==1)//월별 검색
		{
			int monthSelect = Integer.parseInt(request.getParameter("monthSelect"));
			String monthSelect2="";
			
			if(monthSelect <10)
			{
				monthSelect2="0"+monthSelect;
			}
			else
			{
				monthSelect2=monthSelect+"";
			}
			for(int i=0; i<2; i++)
			{
				for(int j=1; j<=5; j++)
				{
					sqlValue += mDAO.createMember(year, i, j, monthSelect2)+",";
				}
			}
			
		}
		else//그냥 연간 검색
		{
			for(int i=0; i<2; i++)
			{
				for(int j=1; j<=5; j++)
				{
					sqlValue += mDAO.createMember(year, i, j)+",";
				}
			}
			
		}
		
		
		
		response.getWriter().write(sqlValue);
	
	}

}






