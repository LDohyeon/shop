package start;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.ProductDAO;
import beans.ProductDTO;



@WebServlet("/startEachProduct.do")
public class StartEachProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int num=Integer.parseInt(request.getParameter("num"));
		ProductDAO pDAO=ProductDAO.getInstance();
		
		ProductDTO pDTO=pDAO.productEach(num);
		
		request.setAttribute("pDTO", pDTO);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("startEachProduct.jsp");
		dispatcher.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
