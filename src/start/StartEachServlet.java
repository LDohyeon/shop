package start;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MainPictureurlDAO;
import DAO.ProductDAO;
import beans.ProductDTO;



@WebServlet("/startEach.do")
public class StartEachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String category=request.getParameter("category");
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage= Integer.parseInt(request.getParameter("lastPage"));
		
		
		ProductDAO pDAO=ProductDAO.getInstance();
		List<ProductDTO> productList=null;
		int pageBtn=0;

		productList=pDAO.selectCategoryProduct(category, startPage, lastPage);
		pageBtn=pDAO.pageBtn();


		if(pageBtn%lastPage==0)
		{
			pageBtn/=lastPage;
		}
		else
		{
			pageBtn/=lastPage;
			pageBtn+=1;
		}
		
		
		MainPictureurlDAO main=MainPictureurlDAO.getInstance();
		String[] mainImage=main.imageSelect();
		
		request.setAttribute("mainImage", mainImage);
			
		request.setAttribute("productList", productList);
		request.setAttribute("pageBtn", pageBtn);
		request.setAttribute("startPage", startPage);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("start.jsp");
		dispatcher.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}










