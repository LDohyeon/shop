package pay;

import java.io.IOException;

import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;
import DAO.PayDAO;
import beans.PayDTO;


@WebServlet("/payList.do")
public class PayListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id=request.getParameter("id");
		
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage=Integer.parseInt(request.getParameter("listPage"));
		
		PayDAO pDAO=PayDAO.getInstance();
		
		List<PayDTO> list=pDAO.payList(id ,startPage, lastPage);
		
		
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
	
		
		int pointHap=0;
		MemberDAO mDAO=MemberDAO.getInstance();
		pointHap=mDAO.selectPointMember(id);
		
		int priceHap=0;
		priceHap=mDAO.selectPriceHapMember(id);
		
		
		request.setAttribute("priceHap", priceHap);
		request.setAttribute("pointHap", pointHap);
		request.setAttribute("payList", list);
		request.setAttribute("payPage", payPage);
		request.setAttribute("startPage", startPage);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("payList.jsp");
		dispatcher.forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
