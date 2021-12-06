package product;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.ProductDAO;
import beans.ProductDTO;



@WebServlet("/productDelete.do")
public class ProductDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String num = request.getParameter("num");
		ProductDAO pDAO= ProductDAO.getInstance();
		
		List<ProductDTO> product=pDAO.selectProductByNum(num);
		
		request.setAttribute("product", product);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("productDelete.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int num= Integer.parseInt(request.getParameter("num"));
		ProductDAO pDAO=ProductDAO.getInstance();
		
		pDAO.deleteProduct(num);
		response.sendRedirect("productList.do?startPage=1&&lastPage=10");
		
	}

}














