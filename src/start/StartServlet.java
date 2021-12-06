package start;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MainPictureurlDAO;
import DAO.ProductDAO;
import beans.ProductDTO;


@WebServlet("/start.do")
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage= Integer.parseInt(request.getParameter("lastPage"));
		

		
		ProductDAO pDAO=ProductDAO.getInstance();
		List<ProductDTO> productList=null;
		int pageBtn=0;

		productList=pDAO.selectProduct(startPage, lastPage);
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
