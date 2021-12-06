package admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.ProductDAO;
import beans.ProductDTO;



@WebServlet("/adminEachMain.do")
public class AdminEachMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int startPage=1;
		int lastPage= 20;
		
		System.out.println("startPage : "+startPage +", lastPage : "+ lastPage);
		
		ProductDAO pDAO=ProductDAO.getInstance();
		List<ProductDTO> productList=null;
		int pageBtn=0;

		productList=pDAO.selectProduct(startPage, lastPage);
		pageBtn=pDAO.pageBtn();


		if(pageBtn%lastPage==0)
		{
			pageBtn/=lastPage;
			System.out.println("pageBtn : "+pageBtn);
		}
		else
		{
			pageBtn/=lastPage;
			pageBtn+=1;
			System.out.println("pageBtn : "+pageBtn);
		}
		
			
		request.setAttribute("productList", productList);
		request.setAttribute("pageBtn", pageBtn);
		request.setAttribute("startPage", startPage);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("adminMain.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
