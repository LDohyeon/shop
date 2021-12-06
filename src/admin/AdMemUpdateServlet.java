package admin;

import java.io.IOException;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.MemberDAO;
import beans.Member;


@WebServlet("/adMemUpdate.do")
public class AdMemUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String id=request.getParameter("id");

		MemberDAO mDAO=MemberDAO.getInstance();
		
		int result =mDAO.adMemUpdate(id);

		if(result==1)
		{
			request.setAttribute("id", id);
			
			Member m= mDAO.getMember(id);
			
			request.setAttribute("pw", m.getPw());
			request.setAttribute("name", m.getName());
			request.setAttribute("phone", m.getPhone());
			request.setAttribute("email", m.getEmail());
			request.setAttribute("ad", m.getAdmin());
			
		}
		else
		{
			request.setAttribute("message" ,"없는 아이디 입니다.");
		}


		RequestDispatcher dispatcher=request.getRequestDispatcher("adMemUpdate.jsp");
		dispatcher.forward(request, response);
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		

		
		

		
		
	}

}




