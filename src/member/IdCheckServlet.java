package member;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.MemberDAO;

@WebServlet("/idCheck.do")
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String id=request.getParameter("id");
		
		
		MemberDAO mDAO=MemberDAO.getInstance();
		
		int result =mDAO.idCheck(id);
		
		response.getWriter().write(result+"");
		
		
		
		
//		if(result==1)//�ߺ�üũ ����
//		{
//			request.setAttribute("result", result);
//			request.setAttribute("id", id);
//			request.setAttribute("color", "green");
//			request.setAttribute("id_check", "��� ������ ���̵��Դϴ�.");
//		}
//		else//�ߺ�üũ ����
//		{
//			request.setAttribute("result", result);
//			request.setAttribute("id", id);
//			request.setAttribute("color", "red");
//			request.setAttribute("id_check", "�ߺ��� ���̵��Դϴ�.");
//		}
		

		
//		RequestDispatcher dispatcher=request.getRequestDispatcher("memberShip.jsp");
//		dispatcher.forward(request, response);
		
		
	}

}






