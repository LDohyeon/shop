package product;

import java.io.IOException;



import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import DAO.ProductDAO;
import beans.ProductDTO;




@WebServlet("/productWrite.do")
public class ProductWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("productWrite.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		ServletContext context=getServletContext();
		
		String path=context.getRealPath("/files");
		
		System.out.println(path);
		String encType="utf-8";
		int sizeLimit=20*1024*1024;
		
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		
		String id=multi.getParameter("id");
		String name=multi.getParameter("name");
		int price=Integer.parseInt(multi.getParameter("price"));
		String maker=multi.getParameter("maker");
		String category=multi.getParameter("category");
		String pictureurl=multi.getFilesystemName("pictureurl");
		String description=multi.getParameter("description");
		int quantity=Integer.parseInt(multi.getParameter("quantity"));
		                                     
		ProductDTO pDTO=new ProductDTO();
		pDTO.setId(id);
		pDTO.setName(name);
		pDTO.setPrice(price);
		pDTO.setMaker(maker);
		pDTO.setCategory(category);
		pDTO.setPictureurl("/files/"+pictureurl);
		pDTO.setDescription(description);
		pDTO.setQuantity(quantity);
		
		
		ProductDAO pDAO=ProductDAO.getInstance();//이렇게 메인 영역 안에서 객체없이 조립하니까 static을 쓴다.

		pDAO.insertProduct(pDTO);
		
		
		int admin = Integer.parseInt(multi.getParameter("admin"));
		String url=null;
		
		if(admin==0)
		{
			url="productList.do?startPage=1&&lastPage=10";
		}
		else if(admin==1)
		{
			url="productList.do?id="+id+"&&startPage=1&&lastPage=10";
		}
		
		response.sendRedirect(url);
		
	}

}











