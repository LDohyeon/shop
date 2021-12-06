package member;

import java.io.IOException;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.MemberDAO;
import beans.Member;


@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url="login.jsp";
		
		
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		String pw =request.getParameter("pw");
		
		MemberDAO mDAO=MemberDAO.getInstance();
		
		int result=mDAO.userCheck(id, pw);
		


		if(result ==3 || result==2 || result==1)
		{
			Member m=mDAO.getMember(id);
			HttpSession session = request.getSession();//������ ����
			session.setAttribute("loginUser", m);
			//request.setAttribute("massage", "�α��ο� �����ϼ̽��ϴ�.");
			url="main.jsp";	//������ ������ Ȱ��
			
			//Member ad = (Member)session.getAttribute("loginUser");

			
		}

		if(result ==0)
		{
			request.setAttribute("check", "1");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
	}

}








