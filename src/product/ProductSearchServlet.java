package product;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MainPictureurlDAO;
import DAO.ProductDAO;
import beans.ProductDTO;



@WebServlet("/productSearch.do")
public class ProductSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String search=request.getParameter("search");
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage= Integer.parseInt(request.getParameter("lastPage"));

		ProductDAO pDAO=ProductDAO.getInstance();
		List<ProductDTO> productList=null;
		int pageBtn=0;

		productList=pDAO.searchProduct(search, startPage, lastPage);
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
		
		MainPictureurlDAO main=MainPictureurlDAO.getInstance();
		String[] mainImage=main.imageSelect();
		
		request.setAttribute("mainImage", mainImage);
			
		request.setAttribute("productList", productList);
		request.setAttribute("pageBtn", pageBtn);
		request.setAttribute("startPage", startPage);
		
		if(productList.size() == 0)
		{
			request.setAttribute("search", "검색 결과가 없습니다.");
		}
		else
		{
			request.setAttribute("search", "검색 결과");
		}
		
		
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("start.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}









