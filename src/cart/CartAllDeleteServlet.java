package cart;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CartDAO;



@WebServlet("/cartAllDelete.do")
public class CartAllDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//전체 삭제 
		
		String id=request.getParameter("id");
		CartDAO cDAO=CartDAO.getInstance();
		cDAO.cartAllDelete(id);
		
		response.sendRedirect("cartList.do?id="+id);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//checkbox가 클릭된 것만 삭제한다.
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		String eachDelete=request.getParameter("eachDelete");
		

		String[] num= eachDelete.split(",");
		CartDAO cDAO=CartDAO.getInstance();
		
		
		for(int i=0; i<num.length; i++)
		{
			cDAO.cartDelete(Integer.parseInt(num[i]));
		}
		
		response.sendRedirect("cartList.do?id="+id);
		
		
	}

}









