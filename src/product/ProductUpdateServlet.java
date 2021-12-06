package product;

import java.io.IOException;

import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import DAO.ProductDAO;
import beans.ProductDTO;



@WebServlet("/productUpdate.do")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String num = request.getParameter("num");
		ProductDAO pDAO= ProductDAO.getInstance();
		
		List<ProductDTO> product=pDAO.selectProductByNum(num);
		
		request.setAttribute("product", product);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("productUpdate.jsp");
		dispatcher.forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		ServletContext context =getServletContext();
		
		String path= context.getRealPath("/files");
		
		System.out.println(path);
		String encType="utf-8";
		int sizeLimit=20*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		
		int num= Integer.parseInt(multi.getParameter("num"));
		String id=multi.getParameter("id");
		String name=multi.getParameter("name");
		int price=Integer.parseInt(multi.getParameter("price"));
		String maker=multi.getParameter("maker");
		String category=multi.getParameter("category");
		String pictureurl=multi.getFilesystemName("pictureurl");
		int quantity=Integer.parseInt(multi.getParameter("quantity"));
		
		int flag=0;
		if(pictureurl == null)
		{
			pictureurl=multi.getParameter("nomakeimg");
			flag=1;
		}                                      

		String description=multi.getParameter("description");
		                                     
		ProductDTO pDTO=new ProductDTO();
		pDTO.setNum(num);
		pDTO.setId(id);
		pDTO.setName(name);
		pDTO.setPrice(price);
		pDTO.setMaker(maker);
		pDTO.setCategory(category);
		
		if(flag==0)
		{
			pDTO.setPictureurl("/files/"+pictureurl);
		}
		if(flag==1)
		{
			pDTO.setPictureurl(pictureurl);
		}
		pDTO.setQuantity(quantity);
		
		pDTO.setDescription(description);
		
		ProductDAO pDAO=ProductDAO.getInstance();
		
		pDAO.updateProduct(pDTO);
		
		response.sendRedirect("productList.do?startPage=1&&lastPage=10");
		
	}

}









