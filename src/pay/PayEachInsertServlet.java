package pay;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;
import DAO.PayDAO;
import beans.PayDTO;



@WebServlet("/payEachInsert.do")
public class PayEachInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String product_name = request.getParameter("product_name");
		String maker= request.getParameter("maker");
		String category = request.getParameter("category");
		int price = Integer.parseInt(request.getParameter("price"));			
		int point =Integer.parseInt(request.getParameter("point"));
		String product_id =request.getParameter("product_id");
		String pictureurl =request.getParameter("pictureurl");
		String quantity =request.getParameter("quantity");


		
		MemberDAO mDAO=MemberDAO.getInstance();
		mDAO.memberPoint(point, id);
		mDAO.memberPriceHap(price, id);
				
		
		PayDTO DTO=new PayDTO();	
		PayDAO DAO=PayDAO.getInstance();
		

			DTO.setId(id);
			DTO.setProduct_name(product_name);
			DTO.setMaker(maker);
			DTO.setCategory(category);
			DTO.setPrice(price+"");
			DTO.setPoint(point);
			DTO.setProduct_id(product_id);
			DTO.setPictureurl(pictureurl);
			DTO.setPurchase_quantity(quantity);
			
			DAO.payInsert(DTO);

	
		//구매를 했으니 장바구니에 있는 것들을 다 삭제한다.
		//개별 구매시 데이터를 하나 보내서 if문을 걸자


		response.sendRedirect("payList.do?id="+id+"&&startPage=1&&listPage=20");
		
	}

}
