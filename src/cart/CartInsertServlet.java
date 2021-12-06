package cart;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CartDAO;
import beans.CartDTO;



@WebServlet("/cartInsert.do")
public class CartInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		int product_num =Integer.parseInt(request.getParameter("product_num"));
		
		System.out.println(product_num);
		String name= request.getParameter("product_name");
		int price=Integer.parseInt(request.getParameter("price"));
		String maker= request.getParameter("maker");
		String category =request.getParameter("category");
		String pictureurl = request.getParameter("pictureurl");
		String description = request.getParameter("description");
		String id = request.getParameter("id");
		int point=Integer.parseInt(request.getParameter("point"));
		String product_id=request.getParameter("product_id");
		int purchase_quantity=Integer.parseInt(request.getParameter("quantity"));
		
		price*=purchase_quantity;
		point*=purchase_quantity;
		
		CartDTO cDTO= new CartDTO();
		
		cDTO.setProduct_num(product_num);
		cDTO.setName(name);
		cDTO.setPrice(price);
		cDTO.setMaker(maker);
		cDTO.setCategory(category);
		cDTO.setPictureurl(pictureurl);
		cDTO.setDescription(description);
		cDTO.setId(id);
		cDTO.setPoint(point);
		cDTO.setProduct_id(product_id);
		cDTO.setPurchase_quantity(purchase_quantity);
		
		CartDAO cDAO = CartDAO.getInstance();
		cDAO.cartInsert(cDTO);
		
		
		response.sendRedirect("cartList.do?id="+id+"");
		
	}

}








