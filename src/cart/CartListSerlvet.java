package cart;

import java.io.IOException;


import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CartDAO;
import beans.CartDTO;
import beans.PayDTO;



@WebServlet("/cartList.do")
public class CartListSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
			
		String id=request.getParameter("id");
	
		CartDAO cDAO= CartDAO.getInstance();
		
		List<CartDTO> cartList=cDAO.cartList(id);
		
		
		int priceHap = 0;
		int pointHap = 0;
		for(int i=0; i<cartList.size(); i++)
		{
			CartDTO cDTO=cartList.get(i);
			priceHap+=cDTO.getPrice();
			pointHap+=cDTO.getPoint();
		}
		


	
		request.setAttribute("cartList", cartList);
		request.setAttribute("priceHap", priceHap);
		request.setAttribute("pointHap", pointHap);	
		
		request.setAttribute("id", id);
				

	
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("cartList.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}







