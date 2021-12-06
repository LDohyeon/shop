package member;

import java.io.IOException;



import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Member;
import DAO.MemberDAO;

@WebServlet("/memberShip.do")
public class MemberShipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher=request.getRequestDispatcher("memberShip.jsp");
		dispatcher.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String id =request.getParameter("id");
		String pw = request.getParameter("pw");
		String name=request.getParameter("name");
		String phone =request.getParameter("phone");
		String email=request.getParameter("email");
		String gender=request.getParameter("gender");
		int age= Integer.parseInt(request.getParameter("age"));
		
		int ages=0;//나이대 정하기
		
		
		if(age<20)
		{
			ages=1;
		}
		else if(age<30)
		{
			ages=2;
		}
		else if(age < 40)
		{
			ages=3;
		}
		else if(age < 50)
		{
			ages=4;
		}
		else if(age >=50)
		{
			ages=5;
		}
				
				
		Member m=new Member();
		
		m.setId(id);
		m.setPw(pw);
		m.setName(name);
		m.setPhone(phone);
		m.setEmail(email);
		m.setGender(gender);
		m.setAge(age);
		m.setAges(ages);
		
		
		MemberDAO mDAO= MemberDAO.getInstance();
		
		int mResult=mDAO.MemberInsert(m);
		
		String url="login.jsp";
		
		if(mResult ==1)
		{
			request.setAttribute("check", "0");
		}
		else
		{
			request.setAttribute("message", "회원가입에 실패하셨습니다.");
			url="memberShip.jsp";
		}
		
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

}










