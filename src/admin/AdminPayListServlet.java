package admin;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;
import DAO.PayDAO;
import beans.PayDTO;



@WebServlet("/adminPayList.do")
public class AdminPayListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id=request.getParameter("id");
		
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage=Integer.parseInt(request.getParameter("listPage"));
		
		PayDAO pDAO=PayDAO.getInstance();
		
		List<PayDTO> list=pDAO.payList(id ,startPage, lastPage);
		
		
		MemberDAO mDAO=MemberDAO.getInstance();
		
		int priceHap = mDAO.selectPriceHapMember(id);
		
		
		int payPage=0;
		payPage=pDAO.pageList(id);
		

		
		if(payPage%lastPage==0)
		{
			payPage/=lastPage;
		}
		else if(payPage%lastPage!=0)
		{
			payPage/=lastPage;
			payPage+=1;
		}
	
		
		
		request.setAttribute("priceHap", priceHap);
		request.setAttribute("id", id);
		request.setAttribute("payList", list);
		request.setAttribute("payPage", payPage);
		request.setAttribute("startPage", startPage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminMemberPayList.jsp");
		dispatcher.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
