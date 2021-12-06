package pay;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.PayDAO;


@WebServlet("/payAllList.do")
public class PayAllListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect("payAllList.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		
		String year=request.getParameter("year");
		
		PayDAO pDAO=PayDAO.getInstance();
		

			String years=null;
			String priceYear = "";
			
			
			
			for(int i=1; i<=12; i++)
			{
				if(i<10)
				{
					years=year+"-0"+i+"-";
				}
				else if(i>=10)
				{
					years=year+"-"+i+"-";
				}
				
				
				if(i<12)
				{
					priceYear += pDAO.YearsPrice(years)+",";
				}
				else if(i>=12)
				{
					priceYear += pDAO.YearsPrice(years);
				}

				
			}
			

			response.getWriter().write(priceYear);
			
		
	
		
		
		
	}

}












