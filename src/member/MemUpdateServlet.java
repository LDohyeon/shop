package member;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Member;
import DAO.MemberDAO;


@WebServlet("/memUpdate.do")
public class MemUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String id= request.getParameter("id");
		String pw= request.getParameter("pw");
		String name=request.getParameter("name");
		String phone =request.getParameter("phone");
		String email =request.getParameter("email");
		String admin=request.getParameter("admin");
		
		if(admin==null)
		{
			admin=request.getParameter("adminCheck");
		}
		
		Member m= new Member();
		
		m.setId(id);
		m.setPw(pw);
		m.setName(name);
		m.setPhone(phone);
		m.setEmail(email);
		m.setAdmin(admin);
		
		MemberDAO mDAO = MemberDAO.getInstance();
		
		int result=mDAO.MemberUpdate(m);
		
		String url="adminMain.do";
		
		if(result ==1)
		{
			request.setAttribute("message", "회원 수정 및 권한 부여를 성공하셨습니다");
		}
		else
		{
			request.setAttribute("message", "회원 수정 및 권한 부여를 실패하였습니다.");
			url="adMemUpdate.do";
		}
		


		response.sendRedirect(url);
		
	}

}











