package product;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MainPictureurlDAO;
import DAO.ProductDAO;
import beans.ProductDTO;


@WebServlet("/productList.do")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id=request.getParameter("id");
		
		
		
		
		int startPage=Integer.parseInt(request.getParameter("startPage"));
		int lastPage= Integer.parseInt(request.getParameter("lastPage"));
		
		

		
		
		ProductDAO pDAO=ProductDAO.getInstance();
		List<ProductDTO> productList=null;
		int pageBtn=0;
		
	
		if(id==null)//���� �Ǹ��� 0���� admin �� ������ Ȩ���������� Ȯ���ϴ� ���
		{
			productList=pDAO.selectProduct(startPage, lastPage);
			pageBtn=pDAO.pageBtn();
		}
		else//���� �Ǹ��ڰ� ������ ���� 1�� �ο��� ���
		{
			productList=pDAO.selectProduct(id, startPage, lastPage);
			pageBtn=pDAO.pageBtn(id);
		}
		
		
		
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
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("productList.jsp");
		dispatcher.forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

}
